package com.example.observability.infrastructure.metrics.aspect

import com.example.observability.infrastructure.metrics.annotation.CounterMetric
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*


@Aspect
@Component
class CounterMetricAspect(
    private val meterRegistry: MeterRegistry
) {

    private val logger = LoggerFactory.getLogger(ElapsedTimeAspect::class.java)

    @Pointcut("@annotation(counterMetric)")
    fun counterMetricPointcut(counterMetric: CounterMetric) {
        logger.info("Pointcut: ${counterMetric.name}")
    }

    @Around("counterMetricPointcut(counterMetric)")
    @Throws(Throwable::class)
    fun around(joinPoint: ProceedingJoinPoint, counterMetric: CounterMetric): Any? {
        val annotations = (joinPoint.signature as MethodSignature).method.annotations
        val counter = Counter.builder(counterMetric.name)
            .description(counterMetric.description)
            .tags(*counterMetric.tags)

        extractTagsFromAnnotations(annotations).forEach(counter::tag)

        var result: Any? = null
        try {
            result = joinPoint.proceed()
        } finally {
            val status = getStatusFromResponse(result)
            counter.tag("status", status)
            counter.register(meterRegistry).increment()
        }
        
        return result
    }

    private fun extractTagsFromAnnotations(annotations: Array<Annotation>): Map<String, String> {
        val tags = mutableMapOf<String, String>()
        annotations.forEach { annotation ->
            when (annotation) {
                is RequestMapping -> {
                    annotation.path.forEach { path ->
                        tags["path"] = path
                    }
                    annotation.method.forEach { method ->
                        tags["method"] = method.name
                    }
                }

                is GetMapping -> {
                    annotation.path.forEach { path ->
                        tags["path"] = path
                    }
                    tags["method"] = "GET"
                }

                is PostMapping -> {
                    annotation.path.forEach { path ->
                        tags["path"] = path
                    }
                    tags["method"] = "POST"
                }

                is PutMapping -> {
                    annotation.path.forEach { path ->
                        tags["path"] = path
                    }
                    tags["method"] = "PUT"
                }

                is DeleteMapping -> {
                    annotation.path.forEach { path ->
                        tags["path"] = path
                    }
                    tags["method"] = "DELETE"
                }
            }
        }
        return tags
    }

    private fun getStatusFromResponse(response: Any?): String =
        when (response) {
            is ResponseEntity<*> -> response.statusCode.toString()
            is Map<*, *> -> response["status"] as String? ?: "UNKNOWN"
            else -> "UNKNOWN"
        }
}
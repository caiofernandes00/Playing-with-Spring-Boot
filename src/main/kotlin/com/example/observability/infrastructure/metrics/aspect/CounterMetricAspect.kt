package com.example.observability.infrastructure.metrics.aspect

import com.example.observability.infrastructure.metrics.annotation.CounterMetric
import com.example.observability.infrastructure.metrics.aspect.utils.Extractor
import com.example.observability.infrastructure.metrics.aspect.utils.Tags
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
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

        Extractor.extractTagsFromAnnotations(annotations).forEach(counter::tag)

        var result: Any? = null
        try {
            result = joinPoint.proceed()
            val status = Extractor.extractStatusFromResponse(result)
            counter.tag(Tags.STATUS, status)
        } finally {
            counter.register(meterRegistry).increment()
        }

        return result
    }

}
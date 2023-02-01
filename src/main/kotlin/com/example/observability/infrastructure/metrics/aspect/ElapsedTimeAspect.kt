package com.example.observability.infrastructure.metrics.aspect

import com.example.observability.infrastructure.metrics.annotation.ElapsedTimeMetric
import com.example.observability.infrastructure.metrics.aspect.utils.Extractor
import com.example.observability.infrastructure.metrics.aspect.utils.Tags
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Aspect
@Component
class ElapsedTimeAspect(
    private val meterRegistry: MeterRegistry
) {

    private val logger = LoggerFactory.getLogger(ElapsedTimeAspect::class.java)

    @Pointcut("@annotation(elapsedTimeMetric)")
    fun timerMetricPointcut(elapsedTimeMetric: ElapsedTimeMetric) {
        logger.info("Pointcut: ${elapsedTimeMetric.name}")
    }

    @Around("timerMetricPointcut(elapsedTimeMetric)")
    @Throws(Throwable::class)
    fun around(joinPoint: ProceedingJoinPoint, elapsedTimeMetric: ElapsedTimeMetric): Any? {
        val startTime = System.currentTimeMillis()

        var result: Any? = null
        var elapsedTime: Long = 0
        try {
            result = joinPoint.proceed()
            elapsedTime = System.currentTimeMillis() - startTime
        } finally {
            timerBuilder(elapsedTimeMetric, joinPoint, elapsedTime, result)

        }
        return result
    }

    private fun timerBuilder(
        elapsedTimeMetric: ElapsedTimeMetric,
        joinPoint: ProceedingJoinPoint,
        elapsedTime: Long,
        result: Any?
    ) {
        val timer = Timer.builder(elapsedTimeMetric.name)
            .description(elapsedTimeMetric.description)
            .tags(*elapsedTimeMetric.tags)

        val tagsFromAnnotations =
            Extractor.extractTagsFromAnnotations((joinPoint.signature as MethodSignature).method.annotations)
        tagsFromAnnotations.forEach(timer::tag)
        val status = Extractor.extractStatusFromResponse(result)

        status?.let { timer.tag(Tags.STATUS, it) }
        timer
            .register(meterRegistry)
            .record(elapsedTime, TimeUnit.MILLISECONDS)
    }

}
package com.example.observability.infrastructure.metrics.aspect

import com.example.observability.infrastructure.metrics.annotation.ElapsedTimeMetric
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
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
    fun around(joinPoint: ProceedingJoinPoint, elapsedTimeMetric: ElapsedTimeMetric): Any {
        val timer = Timer.builder(elapsedTimeMetric.name)
            .description(elapsedTimeMetric.description)
            .tags(*elapsedTimeMetric.tags)
            .register(meterRegistry)

        val startTime = System.currentTimeMillis()
        val result = joinPoint.proceed()
        val timeTaken = System.currentTimeMillis() - startTime

        timer.record(timeTaken, TimeUnit.MILLISECONDS)
        return result
    }

}
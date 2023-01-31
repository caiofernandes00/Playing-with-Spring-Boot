package com.example.observability.infrastructure.metrics.aspect

import com.example.observability.infrastructure.metrics.annotation.TimerMetric
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Aspect
@Component
class TimerMetricAspect(
    private val meterRegistry: MeterRegistry
) {

    @Pointcut("@annotation(timerMetric)")
    fun timerMetricPointcut(timerMetric: TimerMetric) {
    }

    @Around("timerMetricPointcut(timerMetric)")
    @Throws(Throwable::class)
    fun around(joinPoint: ProceedingJoinPoint, timerMetric: TimerMetric): Any {
        val timer = Timer.builder(timerMetric.name)
            .description(timerMetric.description)
            .tags(*timerMetric.tags)
            .register(meterRegistry)

        val startTime = System.currentTimeMillis()
        val result = joinPoint.proceed()
        val timeTaken = System.currentTimeMillis() - startTime

        timer.record(timeTaken, TimeUnit.MILLISECONDS)
        return result
    }

}
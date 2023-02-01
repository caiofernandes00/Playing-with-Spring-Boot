package com.example.observability.infrastructure.metrics.aspect

import com.example.observability.infrastructure.metrics.annotation.CounterMetric
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component


@Aspect
@Component
class CounterMetricAspect(
    private val meterRegistry: MeterRegistry
) {

    @Pointcut("@annotation(counterMetric)")
    fun counterMetricPointcut(counterMetric: CounterMetric?) {
    }

    @Around("counterMetricPointcut(counterMetric)")
    @Throws(Throwable::class)
    fun around(joinPoint: ProceedingJoinPoint, counterMetric: CounterMetric): Any {
        val result = joinPoint.proceed()

        val counter: Counter = Counter.builder(counterMetric.name)
            .description(counterMetric.description)
            .tags(*counterMetric.tags)
            .register(meterRegistry)
        counter.increment()

        return result
    }
}
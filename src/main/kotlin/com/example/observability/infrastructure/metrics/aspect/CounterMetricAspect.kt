package com.example.observability.infrastructure.metrics.aspect

import com.example.observability.infrastructure.metrics.annotation.CounterMetric
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class CounterMetricAspect {

    @Pointcut("@annotation(counterMetric)")
    fun counterMetricPointcut(counterMetric: CounterMetric?) {
        print("test")
    }

    @Around("counterMetricPointcut(counterMetric)")
    @Throws(Throwable::class)
    fun around(joinPoint: ProceedingJoinPoint, counterMetric: CounterMetric): Any {
        print("")
        return joinPoint.proceed()
    }
}
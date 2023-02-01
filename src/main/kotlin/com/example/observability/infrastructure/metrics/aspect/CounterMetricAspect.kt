package com.example.observability.infrastructure.metrics.aspect

import com.example.observability.domain.metrics.CounterMetricRegister
import com.example.observability.infrastructure.metrics.annotation.CounterMetric
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*


@Aspect
@Component
class CounterMetricAspect(
    private val counterMetricRegister: CounterMetricRegister
) {

    private val logger = LoggerFactory.getLogger(ElapsedTimeAspect::class.java)

    @Pointcut("@annotation(counterMetric)")
    fun counterMetricPointcut(counterMetric: CounterMetric) {
        logger.info("Pointcut: ${counterMetric.name}")
    }

    @Around("counterMetricPointcut(counterMetric)")
    @Throws(Throwable::class)
    fun around(joinPoint: ProceedingJoinPoint, counterMetric: CounterMetric): Any? {
        var result: Any? = null
        try {
            result = joinPoint.proceed()
        } finally {
            counterMetricRegister.register(counterMetric, joinPoint, result)
        }

        return result
    }

}
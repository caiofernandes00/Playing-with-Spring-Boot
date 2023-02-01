package com.example.observability.infrastructure.metrics.aspect

import com.example.observability.domain.metrics.ElapsedTimeMetricRegister
import com.example.observability.infrastructure.metrics.annotation.ElapsedTimeMetric
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class ElapsedTimeAspect(
    private val elapsedTimeMetricRegister: ElapsedTimeMetricRegister
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
            elapsedTimeMetricRegister.register(elapsedTimeMetric, joinPoint, elapsedTime, result)
        }

        return result
    }

}
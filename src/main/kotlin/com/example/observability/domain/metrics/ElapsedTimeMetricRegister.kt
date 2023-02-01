package com.example.observability.domain.metrics

import com.example.observability.infrastructure.metrics.annotation.ElapsedTimeMetric
import org.aspectj.lang.ProceedingJoinPoint

interface ElapsedTimeMetricRegister {
    fun register(
        elapsedTimeMetric: ElapsedTimeMetric,
        joinPoint: ProceedingJoinPoint,
        elapsedTime: Long,
        result: Any?
    )
}
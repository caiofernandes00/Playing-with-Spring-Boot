package com.example.observability.domain.metrics

import com.example.observability.infrastructure.metrics.annotation.CounterMetric
import org.aspectj.lang.ProceedingJoinPoint

interface CounterMetricRegister {
    fun register(counterMetric: CounterMetric, joinPoint: ProceedingJoinPoint, result: Any?)
}
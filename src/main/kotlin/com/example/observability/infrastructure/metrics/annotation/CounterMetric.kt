package com.example.observability.infrastructure.metrics.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class CounterMetric(val name: String)

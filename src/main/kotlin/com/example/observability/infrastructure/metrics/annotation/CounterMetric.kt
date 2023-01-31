package com.example.observability.infrastructure.metrics.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class CounterMetric(
    val name: String,
    val description: String,
    vararg val tags: String
)

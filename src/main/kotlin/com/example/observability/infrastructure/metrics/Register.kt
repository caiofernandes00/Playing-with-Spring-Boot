package com.example.observability.infrastructure.metrics

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import org.springframework.context.annotation.Bean

class Register {

    @Bean
    fun meterRegistry(): MeterRegistry {
        return PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
    }
}
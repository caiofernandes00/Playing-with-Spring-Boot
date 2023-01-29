package com.example.observability.infrastructure.metrics.service

import com.google.common.util.concurrent.AtomicDouble
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Service


@Service
class CounterMetric(
    private val meterRegistry: MeterRegistry
) {
    private val counter = meterRegistry.counter("my_counter")
    private val gauge = meterRegistry.gauge("my_gauge", AtomicDouble(0.0))

    fun incrementCounter() {
        counter.increment()
    }

    fun updateGauge(value: Double) {
        gauge?.set(value)
    }
}
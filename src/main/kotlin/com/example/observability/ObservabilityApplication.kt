package com.example.observability

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ObservabilityApplication {
    @Autowired
    fun configureLoki(registry: MeterRegistry) {
        registry.config().commonTags("app_name", "your-app-name")
    }
}

fun main(args: Array<String>) {
    runApplication<ObservabilityApplication>(*args)
}

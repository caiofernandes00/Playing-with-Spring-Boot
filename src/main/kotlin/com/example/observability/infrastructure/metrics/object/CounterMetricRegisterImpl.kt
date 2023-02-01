package com.example.observability.infrastructure.metrics.`object`

import com.example.observability.domain.metrics.CounterMetricRegister
import com.example.observability.infrastructure.metrics.annotation.CounterMetric
import com.example.observability.infrastructure.metrics.aspect.utils.Extractor
import com.example.observability.infrastructure.metrics.aspect.utils.MetricTags
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component

@Component
class CounterMetricRegisterImpl(
    private val meterRegistry: MeterRegistry
) : CounterMetricRegister {

    override fun register(counterMetric: CounterMetric, joinPoint: ProceedingJoinPoint, result: Any?) {
        val counter = Counter.builder(counterMetric.name)
            .description(counterMetric.description)
            .tags(*counterMetric.tags)

        val annotations = (joinPoint.signature as MethodSignature).method.annotations
        Extractor.extractTagsFromAnnotations(annotations).forEach(counter::tag)

        val status = Extractor.extractStatusFromResponse(result)
        counter.tag(MetricTags.STATUS, status)

        counter.register(meterRegistry).increment()
    }
}

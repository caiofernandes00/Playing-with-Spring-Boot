package com.example.observability.infrastructure.metrics.`object`

import com.example.observability.domain.metrics.ElapsedTimeMetricRegister
import com.example.observability.infrastructure.metrics.annotation.ElapsedTimeMetric
import com.example.observability.infrastructure.metrics.aspect.utils.Extractor
import com.example.observability.infrastructure.metrics.aspect.utils.MetricTags
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class ElapsedTimeMetricRegisterImpl(
    private val meterRegistry: MeterRegistry
) : ElapsedTimeMetricRegister {
    override fun register(
        elapsedTimeMetric: ElapsedTimeMetric,
        joinPoint: ProceedingJoinPoint,
        elapsedTime: Long,
        result: Any?
    ) {
        val timer = Timer.builder(elapsedTimeMetric.name)
            .description(elapsedTimeMetric.description)
            .tags(*elapsedTimeMetric.tags)

        val tagsFromAnnotations =
            Extractor.extractTagsFromAnnotations((joinPoint.signature as MethodSignature).method.annotations)
        tagsFromAnnotations.forEach(timer::tag)
        val status = Extractor.extractStatusFromResponse(result)
        timer.tag(MetricTags.STATUS, status)
        timer
            .register(meterRegistry)
            .record(elapsedTime, TimeUnit.MILLISECONDS)
    }
}
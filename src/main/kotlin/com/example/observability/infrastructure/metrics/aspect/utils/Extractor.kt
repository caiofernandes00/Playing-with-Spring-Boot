package com.example.observability.infrastructure.metrics.aspect.utils

import com.example.observability.infrastructure.metrics.aspect.utils.MetricTags.METHOD
import com.example.observability.infrastructure.metrics.aspect.utils.MetricTags.PATH
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


object Extractor {
    internal fun extractStatusFromResponse(response: Any?): String =
        when (response) {
            is ResponseEntity<*> -> response.statusCode.toString()
            is Map<*, *> -> response["status"] as String? ?: "UNKNOWN"
            else -> "UNKNOWN"
        }

    internal fun extractTagsFromAnnotations(annotations: Array<Annotation>): Map<String, String> {
        val tags = mutableMapOf<String, String>()
        annotations.forEach { annotation ->
            when (annotation) {
                is RequestMapping -> {
                    annotation.path.forEach { path ->
                        tags[PATH] = path
                    }
                    annotation.method.forEach { method ->
                        tags[METHOD] = method.name
                    }
                }

                is GetMapping -> {
                    annotation.path.forEach { path ->
                        tags[PATH] = path
                    }
                    tags[METHOD] = HttpMethod.GET.name()
                }

                is PostMapping -> {
                    annotation.path.forEach { path ->
                        tags[PATH] = path
                    }
                    tags[METHOD] = HttpMethod.POST.name()
                }

                is PutMapping -> {
                    annotation.path.forEach { path ->
                        tags[PATH] = path
                    }
                    tags[METHOD] = HttpMethod.PUT.name()
                }

                is DeleteMapping -> {
                    annotation.path.forEach { path ->
                        tags[PATH] = path
                    }
                    tags[METHOD] = HttpMethod.DELETE.name()
                }
            }
        }
        return tags
    }
}

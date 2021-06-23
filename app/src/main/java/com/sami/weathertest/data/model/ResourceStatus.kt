package com.sami.weathertest.data.model

enum class ResourceStatus(val value: Int) {
    LOADING(0),
    SUCCESS(1),
    ERROR(-1)
}

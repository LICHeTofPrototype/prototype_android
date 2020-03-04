package com.example.lichet.exception


class ApiException(override val t: Throwable, override val innerMessage: String?, override val userMessage: String?)
    : AppException(t, innerMessage, userMessage) {
}
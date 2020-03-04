package com.example.lichet.exception

import com.example.lichet.exception.AppException

class CommunicationException(override val t: Throwable, override val innerMessage: String?, override val userMessage: String?)
    : AppException(t, innerMessage, userMessage) {
}
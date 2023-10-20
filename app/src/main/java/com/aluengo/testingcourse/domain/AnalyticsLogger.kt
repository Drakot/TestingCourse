package com.aluengo.testingcourse.domain

interface AnalyticsLogger {
    fun logEvent(key: String, vararg params: LogParam<Any>)
}

data class LogParam<T>(
    val key: String,
    val value: T
)
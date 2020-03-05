package com.example.lichet.api.response

data class HeartBeat(
    var id: Int,
    var pnn: String?,
    var pnn_time: String,
    var measurement: MeasureMent
)
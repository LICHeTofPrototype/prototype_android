package com.example.lichet.api.response

data class HeartBeatResponse(
    var id: Int,
    var pnn_data: Float?,
    var pnn_time: Float,
    var measurement: MeasureMent
)
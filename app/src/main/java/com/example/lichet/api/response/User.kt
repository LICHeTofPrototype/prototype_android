package com.example.lichet.api.response

data class User(
    var id: Int,
    var username: String,
    var first_name: String,
    var last_name: String,
    var login_count: Int
)
package com.example.lichet.api.response

class ErrorResponse(val error: Error){
    class Error(val messages: String)
}

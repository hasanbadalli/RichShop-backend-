package com.example.richshop.response

class ApiResponse<T>(
    val message: String,
    val data: T? = null
)
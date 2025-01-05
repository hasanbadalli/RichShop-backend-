package com.example.richshop.dto

import java.math.BigDecimal

data class ProductUpdateRequest (
    var id: Long? = null,
    var name: String,
    var brand: String,
    var description: String,
    var price: BigDecimal,
    var inventory: Int,
    var categoryIds: List<Long>,
)
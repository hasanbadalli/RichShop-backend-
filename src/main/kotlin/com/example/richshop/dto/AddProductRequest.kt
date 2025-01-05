package com.example.richshop.dto

import com.example.richshop.model.Category
import java.math.BigDecimal

data class AddProductRequest(
    var id: Long? = null,
    var name: String,
    var brand: String,
    var description: String,
    var price: BigDecimal,
    var inventory: Int,
    var categories: MutableSet<Category> = mutableSetOf(),
)
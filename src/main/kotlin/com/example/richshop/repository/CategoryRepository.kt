package com.example.richshop.repository

import com.example.richshop.model.Category
import com.example.richshop.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByName(name: String): Category

    fun existsByName(name: String): Boolean


}
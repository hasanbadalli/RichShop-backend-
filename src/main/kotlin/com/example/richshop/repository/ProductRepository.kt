package com.example.richshop.repository

import com.example.richshop.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    fun findByCategoryName(category: String): List<Product>
    fun findByBrand(brand: String): List<Product>
    fun findByCategoryNameAndBrand(category: String, brand: String): List<Product>
    fun findByName(name: String): List<Product>
    fun findByBrandAndName(brand: String, name: String): List<Product>
    fun countByBrandAndName(brand: String, name: String): Int
}
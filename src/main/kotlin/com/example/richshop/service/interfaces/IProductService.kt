package com.example.richshop.service.interfaces

import com.example.richshop.model.Product
import com.example.richshop.dto.AddProductRequest
import com.example.richshop.dto.ProductUpdateRequest

interface IProductService {
    fun addProduct(request: AddProductRequest): Product
    fun getAllProducts(): List<Product>
    fun getProductById(id: Long): Product
    fun deleteProductById(id: Long)
    fun updateProduct(request: ProductUpdateRequest, productId: Long): Product
    fun getProductsByCategory(category: String): List<Product>
    fun getProductsByBrand(brand: String): List<Product>
    fun getProductsByCategoryAndBrand(category: String, brand: String): List<Product>
    fun getProductsByName(name: String): List<Product>
    fun getProductsByBrandAndName(brand: String, name: String): List<Product>
    fun countProductsByBrandAndName(brand: String, name: String): Int
    fun countProductsByCategory(categoryId: Long): Long
}
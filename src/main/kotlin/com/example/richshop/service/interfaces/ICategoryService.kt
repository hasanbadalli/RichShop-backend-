package com.example.richshop.service.interfaces

import com.example.richshop.model.Category
import com.example.richshop.dto.CategoryDto


interface ICategoryService {
    fun getCategoryById(id: Long): Category
    fun getCategoryByName(name: String): Category
    fun getAllCategories(): List<Category>
    fun addCategory(categoryDto: CategoryDto): Category
    fun updateCategory(category: Category, id: Long): Category
    fun deleteCategory(id: Long)
}
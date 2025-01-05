package com.example.richshop.service.concretes


import com.example.richshop.exceptions.AlreadyExistException
import com.example.richshop.exceptions.ResourceNotFoundException
import com.example.richshop.model.Category
import com.example.richshop.repository.CategoryRepository
import com.example.richshop.dto.CategoryDto
import com.example.richshop.service.interfaces.ICategoryService
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) : ICategoryService {
    private val modelMapper = ModelMapper()
    override fun getCategoryById(id: Long): Category {
        return categoryRepository.findById(id).orElseThrow{ResourceNotFoundException("Category Not Found with ID $id") }
    }

    override fun getCategoryByName(name: String): Category {
        return categoryRepository.findByName(name)
    }

    override fun getAllCategories(): List<Category> {
        return categoryRepository.findAll()
    }

    override fun addCategory(categoryDto: CategoryDto): Category {
        val category: Category = modelMapper.map(categoryDto, Category::class.java)
        return if (!categoryRepository.existsByName(category.name)) {
            categoryRepository.save(category)
        } else {
            throw AlreadyExistException("${category.name} already exists")
        }
    }

    override fun updateCategory(category: Category, id: Long): Category {
        val existingCategory: Category = categoryRepository.findById(id).orElseThrow {ResourceNotFoundException("Category Not Found with ID $id") }
        existingCategory.name = category.name
        return categoryRepository.save(existingCategory)
    }

    override fun deleteCategory(id: Long) {
        return categoryRepository.findById(id).ifPresentOrElse(
            {categoryRepository.delete(it)},
            {throw ResourceNotFoundException("Category Not Found with ID $id") }
        )
    }
}
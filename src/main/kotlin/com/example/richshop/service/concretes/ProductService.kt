package com.example.richshop.service.concretes

import com.example.richshop.exceptions.ProductNotFoundException
import com.example.richshop.model.Category
import com.example.richshop.model.Product
import com.example.richshop.repository.CategoryRepository
import com.example.richshop.repository.ProductRepository
import com.example.richshop.dto.AddProductRequest
import com.example.richshop.dto.ProductUpdateRequest
import com.example.richshop.exceptions.ResourceNotFoundException
import com.example.richshop.service.interfaces.IProductService
import org.modelmapper.ModelMapper
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : IProductService  {
    private val modelMapper = ModelMapper()

    override fun addProduct(request: AddProductRequest): Product {
        val categoryIds: List<Long> = request.categories.map { it.id }
        val existingCategories: List<Category> = categoryRepository.findAllById(categoryIds)

        if(existingCategories.size != categoryIds.size) {
            val missingIds = categoryIds.filterNot { id -> existingCategories.any { it.id == id } }
            throw ResourceNotFoundException("Categories not found: $missingIds")
        }

        return productRepository.save(modelMapper.map(request, Product::class.java))
    }

    private fun createProduct(request: AddProductRequest): Product {
        return Product(
            name = request.name,
            brand = request.brand,
            description = request.description,
            price = request.price,
            inventory = request.inventory,
            categories = request.categories
        )
    }



    override fun getAllProducts(): List<Product> {
        return productRepository.findAll()
    }

    override fun getProductById(id: Long): Product {
        return productRepository.findById(id).orElseThrow{ProductNotFoundException("Product not found")}
    }

    override fun deleteProductById(id: Long) {
            productRepository.findById(id).ifPresentOrElse (
                { product -> productRepository.delete(product) },
                {throw ProductNotFoundException("Product not found with id $id")}
            )
    }

    override fun updateProduct(request: ProductUpdateRequest, productId: Long): Product {
        return productRepository.findById(productId).map{existingProduct-> updateExistingProduct(existingProduct, request)}
            .map{product -> productRepository.save(product)}
            .orElseThrow { ProductNotFoundException("Product not found with id $id") }
    }

    private fun updateExistingProduct(existingProduct: Product, productUpdateRequest: ProductUpdateRequest): Product{
//        existingProduct.name = productUpdateRequest.name
//        existingProduct.brand = productUpdateRequest.brand
//        existingProduct.description = productUpdateRequest.description
//        existingProduct.price = productUpdateRequest.price
//        existingProduct.inventory = productUpdateRequest.inventory
        modelMapper.map(productUpdateRequest, existingProduct)
        val categories: List<Category> = productUpdateRequest.categoryIds.mapNotNull {
            categoryId-> categoryRepository.findById(categoryId).orElse(null)
        }

        existingProduct.categories.clear()
        existingProduct.categories.addAll(categories)

        return existingProduct


    }

    override fun getProductsByCategory(category: String): List<Product> {
        return productRepository.findByCategoryName(category)
    }

    override fun getProductsByBrand(brand: String): List<Product> {
        return productRepository.findByBrand(brand)
    }

    override fun getProductsByCategoryAndBrand(category: String, brand: String): List<Product> {
        return productRepository.findByCategoryNameAndBrand(category, brand)
    }

    override fun getProductsByName(name: String): List<Product> {
        return productRepository.findByName(name);
    }

    override fun getProductsByBrandAndName(brand: String, name: String): List<Product> {
        return productRepository.findByBrandAndName(brand, name);
    }

    override fun countProductsByBrandAndName(brand: String, name: String): Int {
        return productRepository.countByBrandAndName(brand, name)
    }

    override fun countProductsByCategory(categoryId: Long): Long {
        TODO("Not yet implemented")
    }
}
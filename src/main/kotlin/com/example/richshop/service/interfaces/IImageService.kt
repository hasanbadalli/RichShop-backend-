package com.example.richshop.service.interfaces

import com.example.richshop.dto.ImageDto
import com.example.richshop.model.Image
import org.springframework.web.multipart.MultipartFile


interface IImageService {
    fun getImageById(id: Long): Image
    fun deleteImageById(id: Long)
    fun saveImage(files: List<MultipartFile>, productId: Long): List<ImageDto>
    fun updateImage(file: MultipartFile, imageId: Long)
}
package com.example.richshop.service.concretes



import com.example.richshop.dto.ImageDto
import com.example.richshop.repository.ImageRepository
import com.example.richshop.service.interfaces.IImageService
import com.example.richshop.service.interfaces.IProductService
import com.example.richshop.exceptions.ResourceNotFoundException
import com.example.richshop.model.Image
import com.example.richshop.model.Product
import org.modelmapper.ModelMapper
import org.springframework.web.multipart.MultipartFile
import javax.sql.rowset.serial.SerialBlob


class ImageService(
    private val imageRepository: ImageRepository,
    private val productService: IProductService,
): IImageService {
    private val modelMapper = ModelMapper()

    override fun getImageById(id: Long): Image {
        return imageRepository.findById(id).orElseThrow{ResourceNotFoundException("Image not found: $id")}
    }

    override fun deleteImageById(id: Long) {
        return imageRepository.findById(id).ifPresentOrElse(
            {imageRepository.delete(it)},
            {throw ResourceNotFoundException("")}
        )
    }

    override fun saveImage(files: List<MultipartFile>, productId:Long): List<ImageDto> {
        val product: Product = productService.getProductById(productId)
        val savedImageDtos = mutableListOf<ImageDto>()

        files.forEach{file->
            try {
                val image = Image(
                    fileName = file.originalFilename ?: "unknown",
                    fileType = file.contentType ?: "unknown",
                    image = SerialBlob(file.bytes),
                    product = product,
                )
                val savedImage = imageRepository.save(image)

                val imageDto = ImageDto(
                    imageId=savedImage.id!!,
                    imageName = savedImage.fileName,
                    downloadUrl = savedImage.downloadUrl!!
                )

                savedImageDtos.add(imageDto)

            }catch (e: Exception){
                throw RuntimeException ("Failed to save image: ${file.originalFilename}")
            }

        }

        return savedImageDtos
    }

    override fun updateImage(file: MultipartFile, imageId: Long) {
        val image: Image = getImageById(imageId)
        try {
            image.fileName = file.originalFilename.toString()
            image.image = SerialBlob(file.bytes)
            imageRepository.save(image)
        }catch (e: Exception){
            throw RuntimeException(e.message)
        }
    }
}
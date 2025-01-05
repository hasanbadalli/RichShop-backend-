package com.example.richshop.repository

import com.example.richshop.model.Image
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository : JpaRepository<Image, Long> {
}
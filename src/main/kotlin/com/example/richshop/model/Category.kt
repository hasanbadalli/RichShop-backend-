package com.example.richshop.model

import jakarta.persistence.*
import org.springframework.context.annotation.Primary

@Entity
@Table(name = "categories")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    var name: String,

    @ManyToMany(mappedBy = "categories")
    val products: MutableSet<Product> = mutableSetOf()

)

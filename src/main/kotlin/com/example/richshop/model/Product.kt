package com.example.richshop.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var brand: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var price: BigDecimal,

    @Column(nullable = false)
    var inventory: Int,

    @ManyToMany
    @JoinTable(
        name = "product_category",
        joinColumns = [JoinColumn(name = "product_id")],
        inverseJoinColumns = [JoinColumn(name = "category_id")]
    )
    var categories: MutableSet<Category> = mutableSetOf(),

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
    var images: MutableList<Image> = mutableListOf()

)

package com.example.richshop.model

import jakarta.persistence.*
import java.sql.Blob
import javax.sql.rowset.serial.SerialBlob

@Entity
@Table(name = "images")
data class Image(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?=null,
    var fileName: String,
    var fileType: String,

    @Lob
    var image: Blob,

    var downloadUrl: String?=null,

    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product
){
    @PostPersist
    fun setDownloadUrl(){
        if(id != null){
            this.downloadUrl = "api/v1/images/image/download/$id"
        }
    }
}


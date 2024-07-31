package ru.yorymoto.photogallery

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class PhotoGalleryApplication {

    static void main(String[] args) {
        SpringApplication.run(PhotoGalleryApplication, args)
    }

}

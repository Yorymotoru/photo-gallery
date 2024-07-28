package ru.yorymoto.photogallery.service

import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

import ru.yorymoto.photogallery.config.Constants
import ru.yorymoto.photogallery.model.OneDriveResponse

@Service
class OneDriveIntegration {
    private final WebClient webClient

    OneDriveIntegration() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.onedrive.com/")
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "https://api.onedrive.com/"))
                .build()
    }

    @Cacheable("images")
    def getListOfMainImages() {
        def images = webClient
                .get()
                .uri(String.join("", "/v1.0/shares/", Constants.GALLERY_FOLDER_ID, "/root?expand=children"))
                .retrieve()
                .bodyToMono(OneDriveResponse.class) //TODO: Надо попробовать сгенерировать сокращённую версию
                .block()

        return images.children.contentDownloadUrl
    }

    def getListOfWallpapers() {
        def images = webClient
                .get()
                .uri(String.join("", "/v1.0/shares/", Constants.WALLPAPER_FOLDER_ID, "/root?expand=children"))
                .retrieve()
                .bodyToMono(OneDriveResponse.class) //TODO: Надо попробовать сгенерировать сокращённую версию
                .block()

        return images.children.contentDownloadUrl
    }

    Page<String> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize()
        int currentPage = pageable.getPageNumber()
        int startItem = currentPage * pageSize
        List<String> list
        def images = getListOfMainImages()

        if (images.size() < startItem) {
            list = Collections.emptyList()
        } else {
            int toIndex = Math.min(startItem + pageSize, images.size())
            list = images.subList(startItem, toIndex)
        }

        new PageImpl<String>(list, PageRequest.of(currentPage, pageSize), images.size())
    }

}

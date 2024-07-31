package ru.yorymoto.photogallery.service

import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import ru.yorymoto.photogallery.config.Constants
import ru.yorymoto.photogallery.model.ImageForPage
import ru.yorymoto.photogallery.model.OneDriveResponse

import java.time.format.DateTimeFormatter

@Service
class OneDriveIntegration {
    private final WebClient webClient
    private final DateTimeFormatter f = DateTimeFormatter.ofPattern(Constants.TIME_PATTERN)

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

        def thumbnails = webClient
                .get()
                .uri(String.join("", "/v1.0/shares/", Constants.GALLERY_THUMBNAILS_FOLDER_ID, "/root?expand=children"))
                .retrieve()
                .bodyToMono(OneDriveResponse.class) //TODO: Надо автоматизировать процесс
                .block()

        def out = images.children.collect { it ->
            new ImageForPage(
                    name: it.name,
                    href: it.contentDownloadUrl,
                    takenDateTime: Date.parse(
                            Constants.TIME_PATTERN,
                            (it.photo.takenDateTime ?: it.createdDateTime).substring(0, 20)
                    ).getTime(),
                    height: it.image.height,
                    width: it.image.width,
                    thumbnail: thumbnails.children.find(th -> th.name == it.name).contentDownloadUrl
            )
        }

        return out
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

}

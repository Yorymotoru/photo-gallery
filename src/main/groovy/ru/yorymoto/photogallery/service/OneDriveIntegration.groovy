package ru.yorymoto.photogallery.service

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

    def getListOfImages() {
        def images = webClient
                .get()
                .uri(String.join("", "/v1.0/shares/", Constants.GALLERY_FOLDER_ID, "/root?expand=children"))
                .retrieve()
                .bodyToMono(OneDriveResponse.class) //TODO: Надо попробовать сгенерировать сокращённую версию
                .block()

        return images.children.contentDownloadUrl
    }

}

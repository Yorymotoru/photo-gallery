package ru.yorymoto.photogallery.config

import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient

@Configuration
public class OneDriveWebClientConfiguration {
//    private static final String BASE_URL = "https://api.onedrive.com/"
//    public static final int TIMEOUT = 3000
//
//    @Bean
//    public WebClient webClientWithTimeout() {
//        final var tcpClient = TcpClient
//                .create()
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
//                .doOnConnected(connection -> {
//                    connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
//                    connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
//                })
//
//        return WebClient.builder()
//                .baseUrl(BASE_URL)
//                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
//                .build()
//    }
}
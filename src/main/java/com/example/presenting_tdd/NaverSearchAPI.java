package com.example.presenting_tdd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class NaverSearchAPI {
    @Autowired
    WebClient webClient;

    public NaverSearchAPI() {
    }

    SearchResult search(String query, int display, int start, String sort) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", query)
                        .queryParam("display", display)
                        .queryParam("start", start)
                        .queryParam("sort", sort)
                        .build())
                .retrieve()
                .bodyToMono(SearchResult.class)
                .block();
    }

    @Configuration
    static class WebClientConfig {
        @Value("${NAVER_CLIENT_ID}")
        private String clientId;

        @Value("${NAVER_CLIENT_SECRET}")
        private String clientSecret;

        @Bean
        public WebClient webClient() {
            return WebClient.builder()
                    .baseUrl("https://openapi.naver.com/v1/search/local.json")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultHeader("X-Naver-Client-Id", clientId)
                    .defaultHeader("X-Naver-Client-Secret", clientSecret)
                    .build();
        }
    }

    record SearchResult(String lastBuildDate, int total, int start, int display, List<Item> items) {
    }

    record Item(String title, String link, String category, String description, String telephone, String address,
                       String roadAddress, String mapx, String mapy) {
    }
}
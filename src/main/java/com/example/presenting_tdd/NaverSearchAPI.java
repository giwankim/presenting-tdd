package com.example.presenting_tdd;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.publisher.Mono;

@Component
public class NaverSearchAPI {

  private static final String URL = "https://openapi.naver.com";
  private final String naverClientId = System.getenv().get("NAVER_CLIENT_ID");
  private final String naverClientSecret = System.getenv().get("NAVER_CLIENT_SECRET");
  @Autowired
  private Builder webClientBuilder;

  public NaverSearchAPI() {
  }

  public SearchResult search() {
    WebClient webClient = webClientBuilder.baseUrl(URL).build();
    Mono<SearchResult> response = webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/v1/search/local")
            .queryParam("query", "갈비집")
            .queryParam("display", 5)
            .queryParam("start", 1)
            .queryParam("sort", "random")
            .build())
        .header("X-Naver-Client-Id", naverClientId)
        .header("X-Naver-Client-Secret", naverClientSecret)
        .retrieve()
        .bodyToMono(SearchResult.class);
    return response.block();
  }
}

record SearchResult(String lastBuildDate, String total, String start, String display,
                    List<Item> items) {

}

record Item(String title, String link, String category, String description, String telephone,
            String address, String roadAddress, String mapx, String mapy) {

}
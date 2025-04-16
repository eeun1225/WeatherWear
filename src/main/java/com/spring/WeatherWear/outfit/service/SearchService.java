package com.spring.WeatherWear.outfit.service;

import com.spring.WeatherWear.outfit.dto.SearchRequest;
import com.spring.WeatherWear.outfit.dto.SearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class SearchService {
    @Value("${search.client.id}")
    private String client_id;

    @Value("${search.client.secret}")
    private String client_secret;

    @Value("${search.api.url}")
    private String apiUrl;

    public SearchResponse search(String keyword){
        WebClient webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("X-Naver-Client-Id", client_id)
                .defaultHeader("X-Naver-Client-Secret", client_secret)
                .build();

        SearchRequest searchRequest = new SearchRequest(keyword);
        SearchResponse searchResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", "무신사" + searchRequest.getQuery())
                        .queryParam("display", searchRequest.getDisplay())
                        .queryParam("start", searchRequest.getStart())
                        .queryParam("sort", searchRequest.getSort())
                        .build()
                )
                .retrieve()
                .bodyToMono(SearchResponse.class)
                .block();

        return searchResponse;
    }

}

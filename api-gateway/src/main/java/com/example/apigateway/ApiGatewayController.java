package com.example.apigateway;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/orders")
public class ApiGatewayController {

    private final WebClient webClient = WebClient.create("http://order-service:8081");

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String forwardOrder(@RequestBody String body) {
        return webClient.post()
                .uri("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

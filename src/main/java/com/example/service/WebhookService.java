package com.example.service;

import com.example.dto.GenerateWebhookRequest;
import com.example.dto.GenerateWebhookResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Service
public class WebhookService {
    private final WebClient webClient;

    public WebhookService(WebClient webClient) {
        this.webClient = webClient;
    }

    public GenerateWebhookResponse generateWebhook(GenerateWebhookRequest req) {
        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
        return webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(GenerateWebhookResponse.class)
                .block();
    }

    public String submitFinalQuery(String webhookUrl, String accessToken, String finalQuery) {
        Map<String, String> body = Map.of("finalQuery", finalQuery);

        return webClient.post()
                .uri(webhookUrl)
                .headers(h -> {
                    h.set("Authorization", accessToken);  // DIRECT TOKEN - NO "Bearer"
                    h.setContentType(MediaType.APPLICATION_JSON);
                })
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

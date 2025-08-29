package com.example;

import com.example.dto.GenerateWebhookRequest;
import com.example.dto.GenerateWebhookResponse;
import com.example.service.WebhookService;
import com.example.sql.SqlBuilder;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements ApplicationRunner {
    private final WebhookService service;

    public StartupRunner(WebhookService service) {
        this.service = service;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            // PUT YOUR REAL DETAILS HERE
            String name = "Shruti Shukla";       // CHANGE THIS
            String regNo = "22BCE11087";    // CHANGE THIS  
            String email = "shrutishukla297@gmail.com"; // CHANGE THIS
            
            System.out.println("Step 1: Generating webhook...");
            GenerateWebhookRequest request = new GenerateWebhookRequest(name, regNo, email);
            GenerateWebhookResponse response = service.generateWebhook(request);
            
            if (response == null || response.getWebhook() == null || response.getAccessToken() == null) {
                System.out.println("ERROR: Failed to get webhook or access token");
                return;
            }
            
            String webhookUrl = response.getWebhook();
            String accessToken = response.getAccessToken();
            
            System.out.println("Step 2: Got webhook: " + webhookUrl);
            System.out.println("Step 3: Got token length: " + accessToken.length());
            
            // Build final SQL according to Question 1
            String finalSql = SqlBuilder.getFinalQuery();
            System.out.println("Step 4: Submitting final query...");
            
            String result = service.submitFinalQuery(webhookUrl, accessToken, finalSql);
            System.out.println("SUCCESS! Response: " + result);
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}

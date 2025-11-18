package com.capysoft.innovatube.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Service
public class RecaptchaService {
    
    @Value("${recaptcha.secret.key}")
    private String secretKey;
    
    private static final String RECAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify";
    private final RestTemplate restTemplate = new RestTemplate();
    
    public boolean validateToken(String token) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("secret", secretKey);
        params.add("response", token);
        
        Map<String, Object> response = restTemplate.postForObject(RECAPTCHA_URL, params, Map.class);
        
        return response != null && Boolean.TRUE.equals(response.get("success"));
    }
}
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class DemoController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${api.host}")
    private String apiHost;

    @Value("${api.port}")
    private int apiPort;

    @GetMapping("/hello")
    public String hello() {
        Map<String, String> apiResult = restTemplate.getForObject("http://" + apiHost + ":" + apiPort + "/api", Map.class);
        return "api result is: " + apiResult.get("data");
    }
}

package com.example.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map hello() {
        return restTemplate.getForObject("http://" + apiHost + ":" + apiPort + "/api", Map.class);
    }
}

package com.example.dogapi.rest;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class RestClient {

    static RestTemplate restTemplate = new RestTemplate();

    public static ResponseEntity<?> consume (String url){

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<?> result = restTemplate.exchange(
                url, HttpMethod.GET, entity, Object.class);

        return result;
    }
}

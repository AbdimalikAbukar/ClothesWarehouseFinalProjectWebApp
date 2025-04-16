
package com.assignment1.clothes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final String DIST_API_URL = "http://localhost:8081/api/centres/request";

    @GetMapping("/request-item")
    public String showRequestForm() {
        return "request-form";
    }

    @PostMapping("/request-item")
    public String requestItem(@RequestParam String brand,
            @RequestParam String name,
            Model model) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> itemRequest = new HashMap<>();
        itemRequest.put("brand", brand);
        itemRequest.put("name", name);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(itemRequest, headers);

        try {
            ResponseEntity<Boolean> response = restTemplate.postForEntity(DIST_API_URL, request, Boolean.class);
            if (Boolean.TRUE.equals(response.getBody())) {
                model.addAttribute("message", "Stock replenished from distribution centre.");
                return "success";
            } else {
                model.addAttribute("message", "Stock could not be replenished.");
                return "error";
            }
        } catch (Exception e) {
            model.addAttribute("message", "Error connecting to distribution centre.");
            return "error";
        }
    }
}

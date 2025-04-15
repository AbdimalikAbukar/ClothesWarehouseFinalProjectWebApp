package com.assignment1.clothes.client;

import com.assignment1.clothes.model.DistributionCenter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import org.springframework.http.ResponseEntity;
import java.util.List;

@Component
public class DistributionCentreClient {

    private static final String DISTRIBUTION_CENTRE_API_URL = "http://localhost:8081/api/centres"; // URL for the
                                                                                                   // Distribution
                                                                                                   // Centre API

    private final RestTemplate restTemplate;

    public DistributionCentreClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Request an item from the Distribution Centre
    public boolean requestItem(String brand, String name, int quantity) {
        try {
            ItemRequest itemRequest = new ItemRequest(brand, name, quantity); // Prepare request body
            String response = restTemplate.postForObject(DISTRIBUTION_CENTRE_API_URL + "/request", itemRequest,
                    String.class); // Send request

            // If response is "success", return true, else false
            return "success".equals(response);
        } catch (Exception e) {
            return false; // Handle error during API request
        }
    }

    // Fetch all distribution centres
    public List<DistributionCenter> getAllDistributionCentres() {
        try {
            ResponseEntity<List<DistributionCenter>> responseEntity = restTemplate.exchange(
                    DISTRIBUTION_CENTRE_API_URL + "/all",
                    org.springframework.http.HttpMethod.GET,
                    null,
                    new org.springframework.core.ParameterizedTypeReference<List<DistributionCenter>>() {
                    });

            List<DistributionCenter> distributionCentres = responseEntity.getBody();

            // Log the fetched distribution centres
            System.out.println("Distribution Centres: " + distributionCentres);

            return distributionCentres; // Return the list of distribution centers
        } catch (RestClientException e) {
            System.err.println("Error fetching distribution centres: " + e.getMessage());
            return null; // Handle errors (e.g., API is down, malformed response)
        }
    }

    // Inner class representing the request body to the Distribution Centre API
    private static class ItemRequest {
        private String brand; // Changed from brandFrom to match the expected parameter name
        private String itemName; // Changed from 'name' to match expected name
        private int quantity; // Added quantity field for adding an item

        // Constructor for requestItem with quantity
        public ItemRequest(String brand, String itemName, int quantity) {
            this.brand = brand;
            this.itemName = itemName;
            this.quantity = quantity;
        }

        public String getBrand() {
            return brand;
        }

        public String getItemName() {
            return itemName;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
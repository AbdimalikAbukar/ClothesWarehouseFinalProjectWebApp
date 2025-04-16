package com.assignment1.clothes.client;

import com.assignment1.clothes.model.DistributionCenter;
import com.assignment1.clothes.model.ItemResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import org.springframework.http.HttpStatus;
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
    public ItemResponse requestItem(String brand, String name, int quantity) {
        try {
            // Create the request object with the item details
            ItemRequest itemRequest = new ItemRequest(brand, name, quantity);
            System.out.println("Incoming item request: " + itemRequest);

            // Send the POST request and capture the response as an ItemResponse object
            ResponseEntity<ItemResponse> response = restTemplate.postForEntity(
                    DISTRIBUTION_CENTRE_API_URL + "/request", // URL for the distribution center
                    itemRequest, // The item request payload
                    ItemResponse.class); // Expecting an ItemResponse object in return

            // Handle the response
            if (response.getStatusCode() == HttpStatus.OK) {
                // Successfully received item info
                return response.getBody(); // Return the ItemResponse object
            } else {
                // If not successful, return null or an error response
                return null;
            }

        } catch (Exception e) {
            // Handle any exceptions that might occur
            System.err.println("Error requesting item: " + e.getMessage());
            return null; // Return null or an error response
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
        private String brand; // Use brand to match the backend model
        private String name; // Corrected from 'name' to name
        private int quantity; // Added quantity field for adding an item

        // Constructor for requestItem with quantity
        public ItemRequest(String brand, String name, int quantity) {
            this.brand = brand; // Ensure brand is consistent with your API requirements
            this.name = name;
            this.quantity = quantity;
        }

        public String getBrand() {
            return brand; // Correct getter to match the backend field name
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }

        @Override
        public String toString() {
            return "ItemRequest{" +
                    "brand='" + brand + '\'' +
                    ", name='" + name + '\'' +
                    ", quantity=" + quantity +
                    '}';
        }
    }
}

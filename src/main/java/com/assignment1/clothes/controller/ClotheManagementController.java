package com.assignment1.clothes.controller;

import com.assignment1.clothes.Service.ClotheService;
import com.assignment1.clothes.client.DistributionCentreClient;
import com.assignment1.clothes.model.Clothe;
import com.assignment1.clothes.model.DistributionCenter;
import com.assignment1.clothes.model.ItemResponse;
import com.assignment1.clothes.repository.ClotheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/clothes")
public class ClotheManagementController {

    @Autowired
    private ClotheRepository clotheRepository; // Repository for accessing clothing data

    @Autowired
    private ClotheService clotheService;

    @Autowired
    private DistributionCentreClient distributionCentreClient;

    // Serve the clothes management page with the list of clothes and distribution
    // centres
    @GetMapping
    public String manageClothes(Model model) {
        // Fetch all clothes
        List<Clothe> clothes = clotheService.getAllClothes();
        model.addAttribute("clothes", clothes); // Add clothes to the model

        // Fetch all distribution centres
        List<DistributionCenter> distributionCenters = distributionCentreClient.getAllDistributionCentres();
        model.addAttribute("distributionCenters", distributionCenters); // Add distribution centres to the model

        return "clothesManagement"; // Return the correct view for clothes management
    }

    // Delete item by ID (restricted to ADMIN role)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteClothe(@PathVariable Long id) {
        clotheService.deleteClotheById(id); // Delete the clothing item
        return "redirect:/admin/clothes"; // Redirect to the clothes management page
    }

    // Serve the request form page
    @GetMapping("/request-form")
    public String showRequestForm(Model model) {
        List<Clothe> clothes = clotheService.getAllClothesFromDistributionCenters(); // Fetch clothes from the service
        model.addAttribute("clothes", clothes); // Add clothes to the model
        return "request-form"; // The name of the Thymeleaf template
    }

    // Request item from distribution centre (restricted to ADMIN role)
    @PostMapping("/request-item")
    public String requestItem(@RequestParam String name, @RequestParam String brand, @RequestParam int quantity,
            Model model) {
        // Log the item details that are being requested
        System.out.println("Requesting item: Name=" + name + ", Brand=" + brand + ", Quantity=" + quantity);

        // Request item from the distribution center
        ItemResponse response = distributionCentreClient.requestItem(brand, name, quantity);

        // Log the response object to see what we get
        System.out.println("Response from distribution centre: " + response);

        // Check if the response indicates success
        if (response != null && response.isSuccess()) {
            // Assuming the response contains the updated quantity and other missing details
            Clothe clothe = new Clothe();
            clothe.setName(name);
            clothe.setBrand(Clothe.Brand.valueOf(brand.toUpperCase())); // Ensure proper enum mapping
            clothe.setQuantity(response.getUpdatedQuantity()); // Updated quantity from the response
            clothe.setPrice(response.getPrice()); // Assuming price is returned in the response

            // Save the item to the clothes repository
            clotheRepository.save(clothe);

            // Add success message and show updated list of clothes
            model.addAttribute("message", "Item successfully requested and added to the warehouse.");
            return "redirect:/clotheslist"; // Redirect to clothes list page after successful addition
        } else {
            // Log if response is null or if success is false
            System.out.println("Item request failed. Response: " + response);
            model.addAttribute("message", "Sorry, the item could not be replenished.");
        }

        return "request-form"; // Return the appropriate view
    }
}

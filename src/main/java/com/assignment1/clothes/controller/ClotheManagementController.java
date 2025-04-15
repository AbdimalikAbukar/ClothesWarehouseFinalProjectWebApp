package com.assignment1.clothes.controller;

import com.assignment1.clothes.Service.ClotheService;
import com.assignment1.clothes.client.DistributionCentreClient;
import com.assignment1.clothes.model.Clothe;
import com.assignment1.clothes.model.DistributionCenter;

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
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/request-item")
    public String requestItem(@RequestParam Long clotheId, @RequestParam int quantity, Model model) {
        Clothe clothe = clotheService.getClotheById(clotheId);

        if (clothe == null) {
            model.addAttribute("message", "Clothe not found.");
            return "request-form";
        }

        // 🛠 Pass quantity along with brand and name
        boolean success = distributionCentreClient.requestItem(
                clothe.getBrand().getBrandName(),
                clothe.getName(),
                quantity);

        if (success) {
            model.addAttribute("message", "Item successfully requested from distribution centre.");
        } else {
            model.addAttribute("message", "Sorry, the item could not be replenished.");
        }

        return "request-form";
    }

}

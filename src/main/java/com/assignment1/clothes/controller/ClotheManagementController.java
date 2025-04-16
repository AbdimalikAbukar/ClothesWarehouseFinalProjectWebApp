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

    @GetMapping
    public String manageClothes(Model model) {
        // Fetch all clothes
        List<Clothe> clothes = clotheService.getAllClothes();
        model.addAttribute("clothes", clothes); // Add clothes to the model

        // Fetch all distribution centres
        List<DistributionCenter> distributionCenters = distributionCentreClient.getAllDistributionCentres();
        model.addAttribute("distributionCenters", distributionCenters); // Add distribution centres to the model

        return "clothesManagement";
    }

    // Delete item by ID restricted to ADMIN role
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteClothe(@PathVariable Long id) {
        clotheService.deleteClotheById(id);
        return "redirect:/admin/clothes";
    }

    // Serve the request form page
    @GetMapping("/request-form")
    public String showRequestForm(Model model) {
        List<Clothe> clothes = clotheService.getAllClothesFromDistributionCenters(); // Fetch clothes from the service
        model.addAttribute("clothes", clothes);
        return "request-form";
    }

    // Request item from distribution centre restricted to ADMIN role
    @PostMapping("/request-item")
    public String requestItem(@RequestParam String name, @RequestParam String brand, @RequestParam int quantity,
            Model model) {

        ItemResponse response = distributionCentreClient.requestItem(brand, name, quantity);

        if (response != null) {
            Clothe clothe = new Clothe();
            clothe.setName(response.getName());
            clothe.setBrand(response.getBrand());
            clothe.setPrice(response.getPrice());
            clothe.setYearOfCreation(response.getYearOfCreation());
            clothe.setQuantity(response.getQuantity());

            clotheRepository.save(clothe);

            model.addAttribute("message", "Item successfully requested and added to the warehouse.");
            return "redirect:/clotheslist";
        } else {
            model.addAttribute("message", "Sorry, the item could not be replenished.");
        }

        return "request-form";
    }
}

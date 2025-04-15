package com.assignment1.clothes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.assignment1.clothes.model.Clothe;
import com.assignment1.clothes.repository.ClotheRepository;

@Controller
@RequestMapping("/clotheslist")
public class ClothesListController {

    @Autowired
    private ClotheRepository clotheRepository;

    @GetMapping
    public String getClothesList(Model model, Pageable pageable) {
        Page<Clothe> clothesPage = clotheRepository.findAll(pageable);
        model.addAttribute("clothes", clothesPage.getContent());
        model.addAttribute("page", clothesPage);
        return "clotheslist";
    }

    @GetMapping("/filterByBrandAndYear")
    public String filterByBrandAndYear(
            @RequestParam("brand") String brandName,
            @RequestParam("yearOfCreation") int yearOfCreation,
            Model model) {
        Clothe.Brand brand = Clothe.Brand.valueOf(brandName.toUpperCase());
        List<Clothe> filteredClothes = clotheRepository.findByBrandAndYearOfCreation(brand, yearOfCreation);
        model.addAttribute("clothes", filteredClothes);
        return "clotheslist";
    }

    // sorting clothes by brand listed in the order of enum
    @GetMapping("/sortByBrand")
    public String sortByBrand(Model model, Pageable pageable) {

        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("brand"));

        Page<Clothe> sortedClothesPage = clotheRepository.findAll(sortedPageable);
        model.addAttribute("clothes", sortedClothesPage.getContent());
        model.addAttribute("page", sortedClothesPage);
        return "clotheslist";
    }
}

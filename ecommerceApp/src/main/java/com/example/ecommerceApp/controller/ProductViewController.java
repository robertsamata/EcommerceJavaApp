package com.example.ecommerceApp.controller;

import com.example.ecommerceApp.model.Category;
import com.example.ecommerceApp.model.Product;
import com.example.ecommerceApp.repository.CategoryRepository;
import com.example.ecommerceApp.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/products")
public class ProductViewController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Afiseaza formularul de creare produs
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("allCategories", categoryRepository.findAll());
        return "product-form";
    }

    // Primeste formularul, valideaza si salveaza produsul
    @PostMapping
    public String createProduct(@Valid @ModelAttribute("product") Product product,
                                BindingResult bindingResult,
                                @RequestParam(value = "categoryIds", required = false) Set<Long> categoryIds,
                                Model model) {

        if (bindingResult.hasErrors()) {
            // Daca sunt erori, reincarca formularul cu date si erori
            model.addAttribute("allCategories", categoryRepository.findAll());
            return "product-form";
        }

        // Seteaza categoriile selectate
        Set<Category> categories = new HashSet<>();
        if (categoryIds != null) {
            categories.addAll(categoryRepository.findAllById(categoryIds));
        }
        product.setCategories(categories);

        // Salveaza produsul in baza de date
        productRepository.save(product);

        // Redirect la lista produse dupa salvare
        return "redirect:/products";
    }

    // Afiseaza lista produselor
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "product-list";
    }
}

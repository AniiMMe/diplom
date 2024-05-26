package com.example.diplom.controller;

import com.example.diplom.dto.*;
import com.example.diplom.entity.WriteOffProduct;
import com.example.diplom.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/filter")
public class FilterAdminController {
    private final UserService userService;
    private final ClientService clientService;
    private final ProvidersService providersService;
    private final AssortmentService assortmentService;
    private final OrderService orderService;
    private final ProductService productService;
    private final SupplyService supplyService;

    @GetMapping("/admin/users")
    public String getUsersPage(Model model, @ModelAttribute SearchData searchData) {
        model.addAttribute("workers", userService.getAllWorkers());
        return "/admin/users";
    }
    @GetMapping("/admin/clients")
    public  String getClientsPage(Model model, @ModelAttribute SearchData searchData){
        model.addAttribute("clients", clientService.getAllClients());
        return "/admin/admin-clients";
    }
    @GetMapping("/admin/providers")
    public  String getProvidersPage(Model model, @ModelAttribute SearchData searchData){
        model.addAttribute("providers", providersService.getAllProvider());
        return "/admin/admin-providers";
    }
    @GetMapping("/admin/assortment")
    public String getAssortPage(Model model, @ModelAttribute SearchData searchData){
        model.addAttribute("assortments", assortmentService.getAllAssortment());
        return "/admin/admin-ass";
    }
    @GetMapping("/admin/products")
    public  String getProductsPage(Model model, @ModelAttribute SearchData searchData){
        model.addAttribute("products", productService.getAllProducts());
        return "/admin/admin-products";
    }
}

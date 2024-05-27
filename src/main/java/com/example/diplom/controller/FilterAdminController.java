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

    @GetMapping("/users")
    public String getUsersPage(Model model,Authentication authentication, @ModelAttribute SearchData searchData) {
        model.addAttribute("workers", userService.getAllWorkers(searchData));
        model.addAttribute("role", userService.getRole(authentication.getName()));
        model.addAttribute("changeUser", new WorkersDTO());
        return "/admin/users";
    }
    @GetMapping("/clients")
    public  String getClientsPage(Model model, @ModelAttribute SearchData searchData,Authentication authentication){
        model.addAttribute("clients", clientService.getAllClients(searchData));
        model.addAttribute("role", userService.getRole(authentication.getName()));
        model.addAttribute("changeClients", new ClientDTO());
        return "/admin/admin-clients";
    }
    @GetMapping("/providers")
    public  String getProvidersPage(Model model, @ModelAttribute SearchData searchData,Authentication authentication){
        model.addAttribute("providers", providersService.getAllProvider(searchData));
        model.addAttribute("role", userService.getRole(authentication.getName()));
        model.addAttribute("newProvider", new ProvidersDTO());
        return "/admin/admin-providers";
    }
    @GetMapping("/assortment")
    public String getAssortPage(Model model, @ModelAttribute SearchData searchData,Authentication authentication){
        model.addAttribute("assortments", assortmentService.getAllAssortment(searchData));
        model.addAttribute("role", userService.getRole(authentication.getName()));
        model.addAttribute("changeAssort", new AssortmentDTO());
        return "/admin/admin-ass";
    }
    @GetMapping("/products")
    public  String getProductsPage(Model model, @ModelAttribute SearchData searchData,Authentication authentication){
        model.addAttribute("role", userService.getRole(authentication.getName()));
        model.addAttribute("newSpis", new WriteOffProduct());
        model.addAttribute("products", productService.getAllProducts(searchData));
        return "/admin/admin-products";
    }
}

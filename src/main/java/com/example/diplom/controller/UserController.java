package com.example.diplom.controller;

import com.example.diplom.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class UserController {
    private final ClientService clientService;
    private final ProvidersService providersService;
    private final AssortmentService assortmentService;
    private final OrderService orderService;
    private final ProductService productService;
    private final SupplyService supplyService;
    @GetMapping("/user")
    public String getStart() {
        return "/user/userpanel";
    }
    @GetMapping("/user/clients")
    public  String getClientsPage(Model model){
        model.addAttribute("clients", clientService.getAllClients());
        return "/user/user-clients";
}
    @GetMapping("/user/providers")
    public  String getProvidersPage(Model model){
        model.addAttribute("providers", providersService.getAllProvider());
        return "/user/user-providers";
    }
    @GetMapping("/user/assortment")
    public String getAssortPage(Model model){
        model.addAttribute("assortments", assortmentService.getAllAssortment());
        return "/user/user-ass";
    }
    @GetMapping("/user/products")
    public  String getProductsPage(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "/user/user-products";
    }
}
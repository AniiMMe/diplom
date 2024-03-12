package com.example.diplom.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class AdminController {

    @GetMapping("/admin")
    public String getStart() {
        return "/adminpanel";
    }

    @GetMapping("/admin/users")
    public String getUsersPage() {
        return "/users";
    }

    @GetMapping("/admin/clients")
    public  String getClientsPage(){
        return "/admin-clients";
    }
    @GetMapping("/admin/providers")
    public  String getProvidersPage(){
        return "/admin-providers";
    }
    @GetMapping("/admin/assortment")
    public  String getAssortPage(){
        return "/admin-ass";
    }
    @GetMapping("/admin/products")
    public  String getProductsPage(){
        return "/admin-products";
    }
}
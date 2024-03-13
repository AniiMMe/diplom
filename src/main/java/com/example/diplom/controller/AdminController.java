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
    @GetMapping("/admin/orders")
    public  String getOrdersPage(){
        return "/admin-order";
    }
    @GetMapping("/admin/supplies")
    public  String getSuppliesPage(){
        return "/admin-supplies";
    }
    @GetMapping("/admin/newClient")
    public  String getNewClientPage(){
        return "/newClient";
    }
    @GetMapping("/admin/newProvider")
    public  String getNewProviderPage(){
        return "/newProvider";
    }
    @GetMapping("/admin/newAssortment")
    public  String getNewAssortmentrPage(){
        return "/newAss";
    }
    @GetMapping("/admin/newUser")
    public  String getNewUserPage(){
        return "/newUser";
    }
}
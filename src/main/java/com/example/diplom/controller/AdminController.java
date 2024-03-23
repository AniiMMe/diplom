package com.example.diplom.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class AdminController {

    @GetMapping("/admin")
    public String getStart() {
        return "/admin/adminpanel";
    }

    @GetMapping("/admin/users")
    public String getUsersPage() {
        return "/admin/users";
    }

    @GetMapping("/admin/clients")
    public  String getClientsPage(){
        return "/admin/admin-clients";
    }
    @GetMapping("/admin/providers")
    public  String getProvidersPage(){
        return "/admin/admin-providers";
    }
    @GetMapping("/admin/assortment")
    public  String getAssortPage(){
        return "/admin/admin-ass";
    }
    @GetMapping("/admin/products")
    public  String getProductsPage(){
        return "/admin/admin-products";
    }
    @GetMapping("/admin/orders")
    public  String getOrdersPage(){
        return "/admin/admin-order";
    }
    @GetMapping("/admin/invent")
    public  String getInventPage(){
        return "/admin/admin-invent";
    }
    @GetMapping("/admin/supplies")
    public  String getSuppliesPage(){
        return "/admin/admin-supplies";
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
    @GetMapping("/admin/newSupply")
    public  String getNewSypplyPage(){
        return "/newSupply";
    }
    @GetMapping("/admin/newOrder")
    public  String getNewOrderPage(){
        return "/newOrder";
    }
    @GetMapping("/admin/newInvent")
    public  String getNewInventPage(){
        return "/newInvent";
    }

}
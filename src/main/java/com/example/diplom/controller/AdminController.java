package com.example.diplom.controller;

import com.example.diplom.entity.Client;
import com.example.diplom.entity.Workers;
import com.example.diplom.service.ClientService;
import com.example.diplom.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class AdminController {
    private final UserService userService;
    private final ClientService clientService;

    @GetMapping("/admin")
    public String getStart() {
        return "/admin/adminpanel";
    }

    @GetMapping("/admin/users")
    public String getUsersPage(Model model) {
        model.addAttribute("workers", userService.getAllWorkers());
        return "/admin/users";
    }
    @GetMapping("/admin/clients")
    public  String getClientsPage(Model model){
        model.addAttribute("clients", clientService.getAllClients());
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
    public  String getNewClientPage(Model model){
        model.addAttribute("client", new Client());
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
    public  String getNewUserPage(Model model){

        model.addAttribute("user", new Workers());
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
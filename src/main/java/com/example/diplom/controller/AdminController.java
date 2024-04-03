package com.example.diplom.controller;

import com.example.diplom.entity.*;
import com.example.diplom.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class AdminController {
    private final UserService userService;
    private final ClientService clientService;
    private final ProvidersService providersService;
    private final AssortmentService assortmentService;
    private final OrderService orderService;
    private final ProductService productService;
    private final SupplyService supplyService;

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
    public  String getProvidersPage(Model model){
        model.addAttribute("providers", providersService.getAllProvider());
        return "/admin/admin-providers";
    }
    @GetMapping("/admin/assortment")
    public  String getAssortPage(Model model){
        model.addAttribute("assortment", new Assortment());
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
    public  String getNewProviderPage(Model model){
        model.addAttribute("provider", new Providers());
        return "/newProvider";
    }
    @GetMapping("/admin/newAssortment")
    public  String getNewAssortmentrPage(Model model){
        model.addAttribute("assortment", new Assortment());
        return "/newAss";
    }
    @GetMapping("/admin/newUser")
    public  String getNewUserPage(Model model){

        model.addAttribute("user", new Workers());
        return "/newUser";
    }
    @GetMapping("/admin/newSupply")
    public  String getNewSypplyPage(Model model){
        List<Product> products = new ArrayList<>();
        List<String> assortment = new ArrayList<>();
        model.addAttribute("provider", new Supply());
        model.addAttribute("products", products);
        model.addAttribute("assortment", assortment);
        return "/newSupply";
    }
    @GetMapping("/admin/newOrder")
    public  String getNewOrderPage(Model model){
        List<Product> products = new ArrayList<>();
        List<String> assortment = new ArrayList<>();
        model.addAttribute("orders", new Orders());
        model.addAttribute("products", products);
        model.addAttribute("assortment", assortment);
        return "/newOrder";
    }
    @GetMapping("/admin/newInvent")
    public  String getNewInventPage(Model model){
        return "/newInvent";
    }

}
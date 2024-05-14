package com.example.diplom.controller;

import com.example.diplom.dto.OrderDTO;
import com.example.diplom.dto.OrderProductDTO;
import com.example.diplom.entity.StatusOrder;
import com.example.diplom.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

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
    @GetMapping("/user/orders")
    public  String getOrdersPage(Model model){
        model.addAttribute("order", orderService.getAllOrder());
        return "/user/user-order";
    }
    @GetMapping("/user/newOrder")
    public  String getNewOrderPage(Model model){
        List<OrderProductDTO> products = new ArrayList<>();
        List<String> assortment = new ArrayList<>();
        model.addAttribute("orders", new OrderDTO());
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("products", products);
        model.addAttribute("status", StatusOrder.getStatus());
        return "/user/newOrder";
    }
    @GetMapping("/user/supplies")
    public  String getSuppliesPage(Model model){
        model.addAttribute("supplies", supplyService.getAllSupplies());
        return "/user/user-supplies";
    }
    @GetMapping("/user/newSupply")
    public  String getNewSypplyPage(Model model){
        model.addAttribute("provider", providersService.getAllProvider());
        model.addAttribute("supply", new SupplyDTO());
        return "user/newSupply";
    }
}
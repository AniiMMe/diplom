package com.example.diplom.controller;

import com.example.diplom.dto.AssortmentDTO;
import com.example.diplom.dto.ClientDTO;
import com.example.diplom.dto.ProvidersDTO;
import com.example.diplom.dto.SearchData;
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
@RequestMapping("/user/filter")
public class FilterUserContriller {

    private final UserService userService;
    private final ClientService clientService;
    private final ProvidersService providersService;
    private final AssortmentService assortmentService;
    private final OrderService orderService;
    private final ProductService productService;
    private final SupplyService supplyService;
    private final ReturnProductService returnProductService;
    private final InfoForIventService infoForIventService;

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
        model.addAttribute("products", productService.getAllProducts(searchData));
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/admin/admin-products";
    }
    @GetMapping("/orders")
    public  String getOrdersPage(Model model, @ModelAttribute SearchData searchData,Authentication authentication){
        model.addAttribute("order", orderService.getAllOrder(searchData));
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/admin/admin-order";
    }
    @GetMapping("/supplies")
    public  String getSuppliesPage(Model model, @ModelAttribute SearchData searchData,Authentication authentication){
        model.addAttribute("supplies", supplyService.getAllSupplies(searchData));
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/admin/admin-supplies";
    }
    @GetMapping("/returns")
    public String getReturns(Model model, @ModelAttribute SearchData searchData,Authentication authentication) {
        model.addAttribute("returnProductList", returnProductService.getAll(searchData));
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/admin/admin-returns";
    }
}

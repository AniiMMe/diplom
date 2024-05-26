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

    @GetMapping("/user/clients")
    public  String getClientsPage(Model model, @ModelAttribute SearchData searchData){
        model.addAttribute("clients", clientService.getAllClients());
        return "/admin/admin-clients";
    }
    @GetMapping("/user/providers")
    public  String getProvidersPage(Model model, @ModelAttribute SearchData searchData){
        model.addAttribute("providers", providersService.getAllProvider());

        return "/admin/admin-providers";
    }
    @GetMapping("/user/assortment")
    public String getAssortPage(Model model, @ModelAttribute SearchData searchData){
        model.addAttribute("assortments", assortmentService.getAllAssortment());

        return "/admin/admin-ass";
    }
    @GetMapping("/user/products")
    public  String getProductsPage(Model model, @ModelAttribute SearchData searchData){
        model.addAttribute("products", productService.getAllProducts());

        return "/admin/admin-products";
    }
    @GetMapping("/user/orders")
    public  String getOrdersPage(Model model, @ModelAttribute SearchData searchData){
        model.addAttribute("order", orderService.getAllOrder());
        return "/admin/admin-order";
    }
    @GetMapping("/user/supplies")
    public  String getSuppliesPage(Model model, @ModelAttribute SearchData searchData){
        model.addAttribute("supplies", supplyService.getAllSupplies());
        return "/admin/admin-supplies";
    }
    @GetMapping("/user/returns")
    public String getReturns(Model model, @ModelAttribute SearchData searchData) {
        model.addAttribute("returnProductList", returnProductService.getAll());
        return "/admin/admin-returns";
    }
}

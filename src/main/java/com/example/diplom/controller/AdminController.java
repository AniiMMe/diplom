package com.example.diplom.controller;

import com.example.diplom.dto.*;
import com.example.diplom.entity.*;
import com.example.diplom.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
    private final ReturnProductService returnProductService;
    private final WriteOffProductService writeOffProductService;

    @GetMapping("/admin")
    public String getStart(Model model, Authentication authentication) {
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/admin/adminpanel";
    }

    @GetMapping("/admin/users")
    public String getUsersPage(Model model, Authentication authentication) {
        model.addAttribute("workers", userService.getAllWorkers());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        model.addAttribute("changeUser", new WorkersDTO());
        return "/admin/users";
    }
    @GetMapping("/admin/clients")
    public  String getClientsPage(Model model,Authentication authentication){
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        model.addAttribute("changeClients", new ClientDTO());
        return "/admin/admin-clients";
    }
    @GetMapping("/admin/providers")
    public  String getProvidersPage(Model model,Authentication authentication){
        model.addAttribute("providers", providersService.getAllProvider());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        model.addAttribute("newProvider", new ProvidersDTO());
        return "/admin/admin-providers";
    }
    @GetMapping("/admin/assortment")
    public String getAssortPage(Model model,Authentication authentication){
        model.addAttribute("assortments", assortmentService.getAllAssortment());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        model.addAttribute("changeAssort", new AssortmentDTO());
        return "/admin/admin-ass";
    }
    @GetMapping("/admin/products")
    public  String getProductsPage(Model model,Authentication authentication){
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        model.addAttribute("newSpis", new WriteOffProduct());
        return "/admin/admin-products";
    }
    @GetMapping("/admin/productWriteOff")
    public String writeOffProduct(@ModelAttribute WriteOffProduct writeOffProduct){
        writeOffProductService.addProduct(writeOffProduct);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/newClient")
    public  String getNewClientPage(Model model,Authentication authentication){
        model.addAttribute("client", new Client());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/newClient";
    }
    @GetMapping("/admin/newProvider")
    public  String getNewProviderPage(Model model,Authentication authentication){
        model.addAttribute("provider", new Providers());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/newProvider";
    }
    @GetMapping("/admin/newAssortment")
    public  String getNewAssortmentrPage(Model model,Authentication authentication){
        model.addAttribute("assortment", new Assortment());
        model.addAttribute("formV", FormVipuska.getRussianName());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/newAss";
    }
    @GetMapping("/admin/newUser")
    public  String getNewUserPage(Model model,Authentication authentication){

        model.addAttribute("user", new Workers());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/newUser";
    }
    @GetMapping("/admin/analit")
    public String getAnailt(Model model,Authentication authentication) {
        model.addAttribute("role", userService.getRole(authentication.getName()));

        return "/admin/admin-analit";
    }
    @GetMapping("/admin/otch")
    public String getOtch(Model model,Authentication authentication) {
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/admin/admin-otch";
    }
    @GetMapping("/admin/otch2")
    public String getOtch2(Model model,Authentication authentication) {
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/admin/admin-otch2";
    }

}
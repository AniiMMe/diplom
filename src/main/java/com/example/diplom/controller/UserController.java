package com.example.diplom.controller;

import com.example.diplom.service.AssortmentService;
import com.example.diplom.service.ClientService;
import com.example.diplom.service.ProvidersService;
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
}
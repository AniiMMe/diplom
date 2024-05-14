package com.example.diplom.controller;

import com.example.diplom.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class UserController {
    private final ClientService clientService;

    @GetMapping("/user")
    public String getStart() {
        return "/user/userpanel";
    }
    @GetMapping("/user/clients")
    public  String getClientsPage(Model model){
        model.addAttribute("clients", clientService.getAllClients());
        return "/user/user-clients";
}
}
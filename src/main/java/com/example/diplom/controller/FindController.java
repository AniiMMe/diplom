package com.example.diplom.controller;

import com.example.diplom.dto.AssortmentDTO;
import com.example.diplom.dto.ClientDTO;
import com.example.diplom.dto.ProvidersDTO;
import com.example.diplom.dto.WorkersDTO;
import com.example.diplom.entity.Product;
import com.example.diplom.entity.Workers;
import com.example.diplom.service.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class FindController {
    private final UserService userService;
    private final ClientService clientService;
    private final ProvidersService providersService;
    private final ProductService productService;
    private final AssortmentService assortmentService;

    @GetMapping("/find/findOrder/{id}")
    public List<Product> findProductByOrder(@PathVariable int id) {
        return productService.getAllProductsByOrder(id);
    }
    @GetMapping("/find/worker/{id}")
    public WorkersDTO getWorker(@PathVariable int id) {
        return userService.getWorkersByID(id).build();
    }

    @GetMapping("/find/client/{id}")
    public ClientDTO getClient(@PathVariable int id) {
        return clientService.findById(id);
    }

    @GetMapping("/find/provider/{id}")
    public ProvidersDTO getProvider(@PathVariable int id) {
        return providersService.findById(id);
    }
    @GetMapping("/find/findAssort/{id}")
    public AssortmentDTO getAssort(@PathVariable int id){
        return assortmentService.getById(id);
    }
}

package com.example.diplom.controller;

import com.example.diplom.dto.OrderDTO;
import com.example.diplom.dto.OrderDTOMap;
import com.example.diplom.dto.OrderProductDTO;
import com.example.diplom.entity.*;
import com.example.diplom.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Order;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class AdminRestController {
    private final UserService userService;
    private final ClientService clientService;
    private final ProvidersService providersService;
    private final AssortmentService assortmentService;
    private final OrderService orderService;
    private final ProductService productService;
    private final SupplyService supplyService;
    @PostMapping("/admin/newUser")
    public ResponseEntity<String> checkNewUser(@ModelAttribute Workers workers, @RequestParam("userStatys") String status, Model model){
        workers.setRoles(Collections.singleton(UserRole.USER));
        if (status.equals("true")) workers.setActive(true);
        else workers.setActive(false);
        if (userService.checkNewUser(workers) == null) {
            userService.addNewUser(workers);
            return (ResponseEntity<String>) ResponseEntity.ok();
        }
        return ResponseEntity.badRequest().body(userService.checkNewUser(workers));
    }
    @PostMapping("/admin/newClient")
    public ResponseEntity<String>  checkNewClient(@ModelAttribute Client client){
        if (clientService.checkNewUser(client) == null) {
            clientService.addNewUser(client);
            return ResponseEntity.ok("");
        }
        return ResponseEntity.badRequest().body(clientService.checkNewUser(client));
    }
    @PostMapping("/admin/newProvider")
    public ResponseEntity<String>  checkNewProvider(@ModelAttribute Providers provider){
        if (providersService.checkNewProvider(provider) == null) {
            providersService.addNewProvider(provider);
            return ResponseEntity.ok("");
        }
        return ResponseEntity.badRequest().body(providersService.checkNewProvider(provider));
    }
    @PostMapping("/admin/newAssortment")
    public ResponseEntity<String> checkNewAssortment(@ModelAttribute Assortment assortment){
        if (assortmentService.checkNewAssortment(assortment) == null) {
            assortmentService.addNewAssortment(assortment);
            return ResponseEntity.ok("");
        }
        return ResponseEntity.badRequest().body(assortmentService.checkNewAssortment(assortment));
    }
    @PostMapping("/admin/newSupply")
    public ResponseEntity<String> checkNewOrder(@ModelAttribute Supply supply,
                                                @ModelAttribute List<Product> products,
                                                @ModelAttribute List<String> assortment) {
        supplyService.addNewSupply(supply,
                productService.addNewProduct(products, assortment));
        return ResponseEntity.ok("");
    }
    @PostMapping("/admin/newOrder")
    public ResponseEntity<String> checkNewOrder(@ModelAttribute OrderDTOMap data) {
        return ResponseEntity.ok("");
    }
    @GetMapping("/admin/productsList")
    public List<Assortment> getAllAssortment(){
        return assortmentService.getAllAssortment();
    }
}

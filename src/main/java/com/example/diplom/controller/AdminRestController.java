package com.example.diplom.controller;

import com.example.diplom.config.AnswerMessage;
import com.example.diplom.dto.*;
import com.example.diplom.entity.*;
import com.example.diplom.service.*;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Order;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    private final ReturnProductService returnProductService;
    @PostMapping("/admin/newUser")
    public ResponseEntity<String> checkNewUser(@ModelAttribute Workers workers,

                                               Model model){
        workers.setRoles(Collections.singleton(UserRole.USER));
        if (userService.checkNewUser(workers) == null) {
            userService.addNewUser(workers);
            return ResponseEntity.ok("Пользователь успешно добавлен!");
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
    public ResponseEntity<String> checkNewSupply(@ModelAttribute SupplyDTO supply, HttpSession session) {
        List<SupplyProductDTO> supplyProductDTO = (List<SupplyProductDTO>) session.getAttribute("supplyProductDTO");
        supplyService.addNewSupply(supply, supplyProductDTO);
        return ResponseEntity.ok("");
    }
    @PostMapping("/admin/newSupplyProduct")
    public ResponseEntity<String> addSupplyProductInSession(@RequestBody List<SupplyProductDTO> supplyProductDTO,
                                                            HttpSession session) {
        session.setAttribute("supplyProductDTO", supplyProductDTO);
        return ResponseEntity.ok("записан");
    }

    @PostMapping("/admin/newOrder")
    public ResponseEntity<String> checkNewOrder(@ModelAttribute OrderDTO orderDTO, HttpSession session) {
        List<OrderProductDTO> orderProductDTOS = (List<OrderProductDTO>) session.getAttribute("orderProductDTOS");
        orderService.addNewOrder(orderDTO, orderProductDTOS);
        return ResponseEntity.ok("");
    }
    @PostMapping("/admin/newOrderProduct")
    public ResponseEntity<String> addOrderProductInSession(@RequestBody List<OrderProductDTO> orderProductDTOS,
                                                            HttpSession session) {
        session.setAttribute("orderProductDTOS", orderProductDTOS);
        return ResponseEntity.ok("");
    }

    @GetMapping("/admin/productsList")
    public List<Assortment> getAllAssortment(){
        return assortmentService.getAllAssortment();
    }

    @PostMapping("/admin/listForIvent")
    public ResponseEntity<String> addNewInfoForIventInSession(@RequestBody List<InfoDTO> infoDTOS,
                                                         HttpSession session){
        session.setAttribute("listForIvent", infoDTOS);
        return ResponseEntity.ok("");
    }
    @GetMapping("/admin/findOrder/{id}")
    public List<Product> findProductByOrder(@PathVariable int id){
        return productService.getAllProductsByOrder(id);
    }
    @PostMapping("/admin/addNewReturn")
    public ResponseEntity<Map<String, String>> addNewReturn(@RequestBody
                                                                @Valid ReturnProductDto returnProductDto,
                                                            BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(AnswerMessage.getBadMessage(
               returnProductService.checkError(returnProductDto, result)
            ));
        }
        returnProductService.add(returnProductDto);
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Возврат успешно оформлен!"));
    }
}

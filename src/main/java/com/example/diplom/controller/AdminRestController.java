package com.example.diplom.controller;

import com.example.diplom.config.AnswerMessage;
import com.example.diplom.dto.*;
import com.example.diplom.entity.*;
import com.example.diplom.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ResponseEntity<Map<String, String>> checkNewUser(@ModelAttribute @Valid WorkersDTO workers, BindingResult bindingResult,
                                                            @RequestParam("userStatys") String status, Model model) {
        workers.setRoles(Collections.singleton(UserRole.USER));
        if (status.equals("true")) workers.setActive(true);
        else workers.setActive(false);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(AnswerMessage.getBadMessage(userService.checkErrorSwitch(workers, bindingResult)));
        }
        workers.setRoles(Collections.singleton(UserRole.USER));
        userService.addNewUser(workers);
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Пользователь успешно добавлен!"));

    }

    @PostMapping("/admin/newClient")
    public ResponseEntity<Map<String, String>> checkNewClient(@ModelAttribute @Valid ClientDTO client,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            clientService.checkNewUser(client);
            return ResponseEntity.badRequest().body(AnswerMessage.getBadMessage(clientService.checkErrorSwitch(client, bindingResult)));
        }
        clientService.addNewUser(client.build());
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Клиент успешно прошел регистрацию!"));

    }

    @PostMapping("/admin/newProvider")
    public ResponseEntity<Map<String, String>> checkNewProvider(@ModelAttribute @Valid ProvidersDTO provider,
                                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(AnswerMessage.getBadMessage(providersService.checkErrorSwitch(provider, bindingResult)));
        }
        providersService.addNewProvider(provider);
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Поставщик успешно зарегистрирован!"));
    }

    @PostMapping("/admin/newAssortment")
    public ResponseEntity<Map<String, String>> checkNewAssortment(@ModelAttribute @Valid AssortmentDTO assortment,
                                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            assortmentService.checkError(assortment, bindingResult);
            return ResponseEntity.badRequest().body(AnswerMessage
                    .getBadMessage(assortmentService.checkAssErrorSwitch(assortment, bindingResult)));
        }
        assortmentService.addNewAssortment(assortment);
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Ассортимент успешно добавлен!"));
    }

    @PostMapping("/admin/newSupply")
    public ResponseEntity<Map<String, String>> checkNewSupply(@ModelAttribute @Valid SupplyDTO supply,
                                                              BindingResult bindingResult,
                                                              HttpSession session) {
        List<SupplyProductDTO> supplyProductDTO = (List<SupplyProductDTO>) session.getAttribute("supplyProductDTO");
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(AnswerMessage.getBadMessage(supplyService.checkErrorSwitch(supply, supplyProductDTO, bindingResult)));
        }
        supplyService.addNewSupply(supply, supplyProductDTO);
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Паставка успешно оформлена!"));
    }

    @PostMapping("/admin/newSupplyProduct")
    public ResponseEntity<String> addSupplyProductInSession(@RequestBody List<SupplyProductDTO> supplyProductDTO,
                                                            HttpSession session) {
        session.setAttribute("supplyProductDTO", supplyProductDTO);
        return ResponseEntity.ok("записан");
    }

    @PostMapping("/admin/newOrder")
    public ResponseEntity<Map<String, String>> checkNewOrder(@ModelAttribute @Valid OrderDTO orderDTO,
                                                             BindingResult bindingResult,
                                                             HttpSession session) {
        List<OrderProductDTO> orderProductDTOS = (List<OrderProductDTO>) session.getAttribute("orderProductDTOS");
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(AnswerMessage.getBadMessage(orderService.checkErrorSwitch(orderDTO, orderProductDTOS, bindingResult)));
        }
        orderService.addNewOrder(orderDTO, orderProductDTOS);
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Паставка успешно оформлена!"));
    }

    @PostMapping("/admin/newOrderProduct")
    public ResponseEntity<String> addOrderProductInSession(@RequestBody
                                                           List<OrderProductDTO> orderProductDTOS,
                                                           HttpSession session) {
        session.setAttribute("orderProductDTOS", orderProductDTOS);
        return ResponseEntity.ok("");
    }

    @GetMapping("/admin/productsList")
    public List<Assortment> getAllAssortment() {
        return assortmentService.getAllAssortment();
    }

    @PostMapping("/admin/listForIvent")
    public ResponseEntity<String> addNewInfoForIventInSession(@RequestBody
                                                              List<InfoDTO> infoDTOS,
                                                              HttpSession session) {
        session.setAttribute("listForIvent", infoDTOS);
        return ResponseEntity.ok("");
    }

    @GetMapping("/admin/findOrder/{id}")
    public List<Product> findProductByOrder(@PathVariable int id) {
        return productService.getAllProductsByOrder(id);
    }


    @PostMapping("/admin/addNewReturn")
    public ResponseEntity<Map<String, String>> addNewReturn(@RequestBody
                                                            @Valid ReturnProductDto returnProductDto,
                                                            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(AnswerMessage.getBadMessage(
                    returnProductService.checkError(returnProductDto, result)
            ));
        }
        returnProductService.add(returnProductDto);
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Возврат успешно оформлен!"));
    }

    @GetMapping("/admin/getListCountProduct")
    public Map<String, Integer> getAllCountByProduct() {
        return assortmentService.getAllAssortment().stream()
                .collect(Collectors.toMap(
                        Assortment::getProductName,
                        Assortment::getCount
                ));
    }
}

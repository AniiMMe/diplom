package com.example.diplom.controller;

import com.example.diplom.config.AnswerMessage;
import com.example.diplom.dto.*;
import com.example.diplom.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final OrderService orderService;
    private final ClientService clientService;
    private final ProvidersService providersService;
    private final SupplyService supplyService;

    @PostMapping("/user/newClient")
    public ResponseEntity<Map<String, String>> checkNewClient(@ModelAttribute @Valid ClientDTO client,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            clientService.checkNewUser(client);
            return ResponseEntity.badRequest().body(AnswerMessage.getBadMessage(clientService.checkErrorSwitch(client, bindingResult)));
        }
        clientService.addNewUser(client.build());
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Клиент успешно прошел регистрацию!"));

    }
    @PostMapping("/user/changeProvider/{id}")
    public ResponseEntity<Map<String, String>> checkChangeProvider(@ModelAttribute @Valid ProvidersDTO provider,
                                                                   BindingResult bindingResult, @PathVariable int id) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(AnswerMessage.getBadMessage(providersService.checkErrorSwitch(provider, bindingResult)));
        }
        providersService.changeProvider(provider, id);
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Поставщик успешно зарегистрирован!"));
    }
    @PostMapping("/user/newSupply")
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
    @PostMapping("/user/newOrder")
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
}

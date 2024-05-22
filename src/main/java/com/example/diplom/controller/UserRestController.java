package com.example.diplom.controller;

import com.example.diplom.config.AnswerMessage;
import com.example.diplom.dto.*;
import com.example.diplom.entity.Assortment;
import com.example.diplom.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    private final AssortmentService assortmentService;
    private final InfoForIventService infoForIventService;

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
    @GetMapping("/user/productsList")
    public List<Assortment> getAllAssortment() {
        return assortmentService.getAllAssortment();
    }

    @PostMapping("/user/newOrderProduct")
    public ResponseEntity<String> addOrderProductInSession(@RequestBody
                                                           List<OrderProductDTO> orderProductDTOS,
                                                           HttpSession session) {
        session.setAttribute("orderProductDTOS", orderProductDTOS);
        return ResponseEntity.ok("");
    }


    @PostMapping("/user/newSupplyProduct")
    public ResponseEntity<String> addSupplyProductInSession(@RequestBody List<SupplyProductDTO> supplyProductDTO,
                                                            HttpSession session) {
        session.setAttribute("supplyProductDTO", supplyProductDTO);
        return ResponseEntity.ok("записан");
    }
    @PostMapping("/user/listForIvent")
    public ResponseEntity<String> addNewInfoForIventInSession(@RequestBody
                                                              List<InfoDTO> infoDTOS,
                                                              HttpSession session) {
        session.setAttribute("listForIvent", infoDTOS);
        return ResponseEntity.ok("");
    }
    @PostMapping("/user/addNewIvent")
    public ResponseEntity<Map<String,String>> getInfoForIvent(@ModelAttribute @Valid InfoForIventDTO infoForIventDTO,
                                  BindingResult result,
                                  Model model, Authentication authentication, HttpSession session){
        List<InfoDTO> infoDTOS = (List<InfoDTO>) session.getAttribute("listForIvent");
//        if (result.hasErrors()){
//            return ResponseEntity.badRequest().body(AnswerMessage.getBadMessage(infoForIventService.checkIvent(infoForIventDTO, result)));
//        }
        infoForIventDTO.setInfoDTOS(infoDTOS);
        session.setAttribute("infoForIvent", assortmentService.addInfoForIvent(infoForIventDTO));
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Инвентаризация сформирована!"));
    }


}

package com.example.diplom.controller;

import com.example.diplom.config.AnswerMessage;
import com.example.diplom.dto.ClientDTO;
import com.example.diplom.dto.ProvidersDTO;
import com.example.diplom.service.ClientService;
import com.example.diplom.service.ProductService;
import com.example.diplom.service.ProvidersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final ClientService clientService;
    private final ProvidersService providersService;

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
}

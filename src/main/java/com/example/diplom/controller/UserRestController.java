package com.example.diplom.controller;

import com.example.diplom.config.AnswerMessage;
import com.example.diplom.dto.ClientDTO;
import com.example.diplom.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final ClientService clientService;

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
}

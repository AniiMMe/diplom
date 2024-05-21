package com.example.diplom.controller;

import com.example.diplom.dto.ClientDTO;
import com.example.diplom.dto.WorkersDTO;
import com.example.diplom.entity.Workers;
import com.example.diplom.service.ClientService;
import com.example.diplom.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class FindController {
    private final UserService userService;
    private final ClientService clientService;

    @GetMapping("/find/worker/{id}")
    public WorkersDTO getWorker(@PathVariable int id){
        return userService.getWorkersByID(id).build();
    }
    @GetMapping("/find/client/{id}")
    public ClientDTO getClient(@PathVariable int id){
        return clientService.findById(id);
    }
}

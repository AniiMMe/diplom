package com.example.diplom.controller;

import com.example.diplom.entity.UserRole;
import com.example.diplom.entity.Workers;
import com.example.diplom.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@AllArgsConstructor
@RestController
public class AdminRestController {
    private final UserService userService;
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
}

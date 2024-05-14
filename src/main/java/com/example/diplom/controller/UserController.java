package com.example.diplom.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class UserController {
    @GetMapping("/user")
    public String getStart() {
        return "/user/userpanel";
    }
}

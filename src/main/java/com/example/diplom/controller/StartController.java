package com.example.diplom.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class StartController {
    @GetMapping("/")
    public String getStart()
    {
        return "/main";
    }
    //добавишь страницу входа
    @GetMapping("/login")
    public String  getLogIn(){ return "/logIn";}
}

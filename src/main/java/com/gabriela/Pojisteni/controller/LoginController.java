package com.gabriela.Pojisteni.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    /* Spring má defaultní formulář pro login,
        který je však v angličtině, odkazuji tedy na vlastní html */
    @RequestMapping("/login")
    public String login() {
        return "login.html";
    }
}

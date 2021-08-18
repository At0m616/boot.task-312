package com.crud.demo311.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginLogoutController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }



}

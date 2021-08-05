package com.crud.demo311.controller;

import com.crud.demo311.model.User;
import com.crud.demo311.service.RoleService;
import com.crud.demo311.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.validation.Valid;
import java.security.Principal;


@Controller
public class UsersController {
    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/registration")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listRoles", roleService.findAllRoles());
        return "registration";
    }
    @PostMapping("/registration")
    public String createNewUser(@ModelAttribute("user") @Valid User userForm, BindingResult bindingResult,
                                @RequestParam(required = false, name = "roles") Long[] rolesId) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.saveUser(userForm, rolesId);
        return "redirect:/admin";
    }



    @GetMapping("/user")
    public String userData(Principal principal, Model model) {
        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "user-info";
    }

}

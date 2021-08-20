package com.crud.demo311.controller;

import com.crud.demo311.model.User;
import com.crud.demo311.service.RoleService;
import com.crud.demo311.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    private final RoleService roleService;


    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAllUsers(Model model, @AuthenticationPrincipal User user) {

        model.addAttribute("listRoles", roleService.findAllRoles());

        List<User> userList = userService.findAllUsers();
        userList.sort(Comparator.comparing(User::getUsername));
        model.addAttribute("listU", userList);

        model.addAttribute("user", user);
        model.addAttribute("newUser", new User());
        return "admin-page";
    }


    @PostMapping("/user")
    public String createNewUser(@ModelAttribute("user") @Valid User userForm, BindingResult bindingResult,
                                @RequestParam(required = false, name = "roles") Long[] rolesId) {
        if (bindingResult.hasErrors()) {
            return "admin-page";
        }
        userService.saveUser(userForm, rolesId);

        return "redirect:/admin";
    }


    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user,
                             @RequestParam(required = false, name = "roles") Long[] roles) {
        userService.updateUser(user, roles);

        return "redirect:/admin";
    }

    @PostMapping("/delete/{userId}")
    public String removeUser(@PathVariable("userId") Long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }


}

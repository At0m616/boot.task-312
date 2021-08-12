package com.crud.demo311.controller;

import com.crud.demo311.model.Role;
import com.crud.demo311.model.User;
import com.crud.demo311.service.RoleService;
import com.crud.demo311.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
    public String getAllUsers(Principal principal, Model model, @AuthenticationPrincipal User user) {
//        User loginUser = userService.findUserByEmail(principal.getName());
//        model.addAttribute("loginUser", loginUser);

        model.addAttribute("listRoles", roleService.findAllRoles());
        List<User> userList =userService.findAllUsers();
        userList.sort(Comparator.comparing(User::getUsername));
        model.addAttribute("listU", userList);

        model.addAttribute("user", user);
        model.addAttribute("newUser", new User());
        return "admin-page";
    }

//    @GetMapping("/new-user")
//    public String newUser(Model model) {
//
//        return "registration";
//    }
    @PostMapping("/user")
    public String createNewUser(@ModelAttribute("user") @Valid User userForm,
                                @RequestParam(required = false, name = "roles") Long[] rolesId) {
        Set<Role> rolesSet = new HashSet<>();
        for (long id : rolesId) {
            rolesSet.add(roleService.findById(id));
        }
        userForm.setRoles(rolesSet);

        userService.saveUser(userForm, rolesId);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        List<Role> roles = roleService.findAllRoles();
        model.addAttribute("getUserById", user);
        for(Role r : roles){
        System.out.println(user.getRoles().contains(r));
        }
        model.addAttribute("listRoles", roles );
        return "edit";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             @RequestParam(required = false, name = "roles") Long[] roles) {
        userService.saveUser(user, roles);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String removeUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("{id}")
    public String userInfo(@PathVariable("id")int id, Model model){
        model.addAttribute("user", userService.findUserById(id));
        return "user-info";
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.controller;

import com.jamesyoudom.College_Grading_Sys.security.Users;
import com.jamesyoudom.College_Grading_Sys.security.UsersRepository;
import com.jamesyoudom.College_Grading_Sys.service.UsersService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author youdo..........
 */
@Controller
public class SecurityController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UsersService usersService;


    @GetMapping("/login")
    public String login(@RequestParam(value = "logout", defaultValue = "false") boolean loginError, Model model) {
        Users user = new Users();
        List<Users> users = usersRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("user", user);

        if (loginError){
            String message = "You have successfully been logged out!";
            model.addAttribute("message", message);
            return"index";
        }

        return "login/login";

    }

    @GetMapping("/home")
    public String getHome(Model model) {
        Users user = getCurrentUser();
        model.addAttribute("user", user);
        return "index";
    }



    public Users getCurrentUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return usersService.findByUsersname(username);
        } else {
            String username = principal.toString();
            return usersService.findByUsersname(username);
        }
    }

}

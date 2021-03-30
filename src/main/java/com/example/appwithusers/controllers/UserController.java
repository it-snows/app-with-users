package com.example.appwithusers.controllers;

import com.example.appwithusers.data.UserRepository;
import com.example.appwithusers.dto.UserLoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private UserRepository repo;

    public UserController() {
        repo = new UserRepository();
    }

    @GetMapping("")
    public String getIndex(Model model) {
        model.addAttribute("error", "");
        model.addAttribute("hasError", false);
        return "login";
    }

    @PostMapping("")
    public String login(UserLoginDto userData, Model model) {

        var user = repo.login(userData.getEmail(), userData.getPwd());

        if (user == null) {
            model.addAttribute("error", "Unable to login");
            model.addAttribute("hasError", true);
            return "login";
        }
        return "profile";
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {



        return "profile";
    }
}

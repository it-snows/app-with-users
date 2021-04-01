package com.example.appwithusers.controllers;

import com.example.appwithusers.data.User;
import com.example.appwithusers.data.UserRepository;
import com.example.appwithusers.dto.UserLoginDto;
import com.example.appwithusers.session.SessionData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    //HttpSession session
    @PostMapping("")
    public String login(UserLoginDto userData, Model model, HttpServletRequest request) {

        var user = repo.login(userData.getEmail(), userData.getPwd());

        if (user == null) {
            model.addAttribute("error", "Unable to login");
            model.addAttribute("hasError", true);
            return "login";
        }

        request.getSession().setAttribute(SessionData.User, user);

        model.addAttribute("user", user);

        return "profile";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, HttpSession session) {

        var user = (User) session.getAttribute(SessionData.User);

        model.addAttribute("user", user);
        model.addAttribute("sessionId", session.getId());
        return "profile";
    }
}
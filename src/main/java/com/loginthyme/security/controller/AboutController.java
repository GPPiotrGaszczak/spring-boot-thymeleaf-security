package com.loginthyme.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {
    @GetMapping("/about")
    public String displayAboutPage(Model model, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        model.addAttribute("isAboutPage", "/about".equals(requestURI));
        return "about.html";
    }
}
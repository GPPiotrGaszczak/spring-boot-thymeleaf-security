package com.loginthyme.security.controller;

import com.loginthyme.security.model.MyUser;
import com.loginthyme.security.model.Role;
import com.loginthyme.security.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.Collections;

@Controller
@Slf4j
public class RegisterController {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/createUser")
    public String createUser(@Valid @ModelAttribute("MyUser") MyUser myUser, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String passwordMatchingMessage = bindingResult.getAllErrors().toString();
            if(passwordMatchingMessage.contains("The password fields must match")) {
                bindingResult.rejectValue("confirmPassword",
                        "error.confirmPassword",
                        "The password fields must match");
            }

            return "register";
        }
        if(!userRepository.existsByEmail(myUser.getEmail())) {
            myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
            myUser.setRole(Collections.singleton(Role.ROLE_USER));
            userRepository.save(myUser);
            log.info("User succesfully registered");
        } else {
            log.info("Such a user already exists");
        }
        return "redirect:/register";
    }

    @GetMapping("/register")
    public String displayRegisterPage(Model model) {
        model.addAttribute("MyUser", new MyUser());
        return "register";
    }
}
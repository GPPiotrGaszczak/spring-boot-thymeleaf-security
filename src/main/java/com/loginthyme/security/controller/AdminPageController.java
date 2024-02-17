package com.loginthyme.security.controller;

import com.loginthyme.security.model.MyUser;
import com.loginthyme.security.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminPageController {
    UserRepository userRepository;

    @Autowired
    public AdminPageController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/admin-page")
    public String displayAdminPage(Model model, HttpServletRequest httpServletRequest) {
        String url = httpServletRequest.getRequestURI();
        model.addAttribute("isAdminPage", "/admin-page".equals(url));
        displayUsers(model);
        return "admin-page";
    }

    public void displayUsers(Model model) {
        List<MyUser> users = userRepository.findByRole("ROLE_USER");
        model.addAttribute("users", users);
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUserById(@PathVariable(required = false) String id) {
        if(id != null) {
            MyUser user = userRepository.findById(id);
            if(user != null) {
                userRepository.delete(user);
            }
        }
        return "redirect:/admin-page";
    }

    @GetMapping("/deleteUser/{email}")
    public String deleteUserByEmail(@PathVariable(required = false) String email) {
        if(email != null) {
            MyUser user = userRepository.findByEmail(email);
            if(user != null) {
                userRepository.delete(user);
            }
        }
        return "redirect:/admin-page";
    }
}
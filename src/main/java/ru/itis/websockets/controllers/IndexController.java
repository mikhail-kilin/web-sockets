package ru.itis.websockets.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Controller
public class IndexController {
    @GetMapping("/")
    public String getIndexPage(Model model) {
        model.addAttribute("pageId", UUID.randomUUID().toString());
        return "index";
    }
}

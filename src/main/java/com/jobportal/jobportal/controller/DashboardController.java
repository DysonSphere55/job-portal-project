package com.jobportal.jobportal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashBoardPage(Model model) {
        return "dashboard";
    }
}

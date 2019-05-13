package com.capstone.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CompareController {

    @RequestMapping("/compare")
    public String compare() {
        return "compare";
    }

    @GetMapping("/compare_login")
    public String compare_login() {
        return "compare_login";
    }

    @GetMapping("/compare_result_login")
    public String compare_result_login() {
        return "compare_result_login";
    }

}

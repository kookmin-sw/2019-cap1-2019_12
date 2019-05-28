package com.capstone.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SubmitController {

    @RequestMapping("/submit")
    public String submit() {
        return "submit";
    }

    @GetMapping("/submit_login")
    public String submit_login() {
        return "submit_login";
    }

}

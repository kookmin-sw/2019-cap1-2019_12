package com.capstone.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultController {

    @GetMapping("/result")
    public String result(Model model) {
        return "result";
    }

    @GetMapping("/result_login")
    public String result_login(){
        return "result_login";
    }

}

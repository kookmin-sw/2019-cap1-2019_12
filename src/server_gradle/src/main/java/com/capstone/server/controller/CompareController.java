package com.capstone.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CompareController {

    @RequestMapping("/compare")
    public String compare() {
        return "compare";
    }

}

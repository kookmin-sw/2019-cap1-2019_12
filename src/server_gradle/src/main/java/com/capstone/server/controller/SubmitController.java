package com.capstone.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SubmitController {

    @RequestMapping("/submit")
    public String submit() {
        return "submit";
    }

}

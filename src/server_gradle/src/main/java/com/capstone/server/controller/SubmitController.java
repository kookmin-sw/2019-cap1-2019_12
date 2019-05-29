package com.capstone.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class SubmitController {

    @RequestMapping(value = "/submit", method = RequestMethod.GET)
    public String submit(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "submit";
    }


}

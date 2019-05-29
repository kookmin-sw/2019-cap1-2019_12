package com.capstone.server.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@org.springframework.stereotype.Controller
public class Controller {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String home(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "main_login";
    }

//    private DbTest dbTest;

//    @GetMapping("/dbtest")
//    public void dbtest() throws Exception {
//        dbTest.main();
//
//    }


    @RequestMapping(value="/request", method = RequestMethod.GET)
    public String request(String str) {

        return "the request message is " + str;
    }


}

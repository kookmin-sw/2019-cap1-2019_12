package com.capstone.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class Controller {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Principal home(Principal principal) {
        return principal;
    }


}

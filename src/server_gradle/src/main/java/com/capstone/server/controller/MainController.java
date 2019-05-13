package com.capstone.server.controller;

import com.capstone.server.config.SessionConstants;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class MainController {

    private HttpSession httpSession;

    public MainController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @GetMapping("/me")
    public Map<String, Object> me() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("profile", httpSession.getAttribute(SessionConstants.LOGIN_USER));
        return response;
    }

}

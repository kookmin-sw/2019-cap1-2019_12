package com.capstone.server.controller;

//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(auth.getPrincipal());
        return "main";
    }

   /* @GetMapping("/callback")
    public String callback() {
        return "main";
    }*/

   @GetMapping("/main_login")
   public String main_login() {
       return "main";
   }

   @GetMapping("/callback")
    public String callback() {
       return "main";
   }
}

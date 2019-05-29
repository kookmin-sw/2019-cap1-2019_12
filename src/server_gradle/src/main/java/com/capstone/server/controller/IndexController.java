package com.capstone.server.controller;

//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(auth.getPrincipal());
        return "main";
    }

//    @GetMapping("/main")
//    public String index_main() {
////        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////        System.out.println(auth.getPrincipal());
//        return "main_login";
//    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String index_main(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "main_login";
    }

   /* @GetMapping("/callback")
    public String callback() {
        return "main";
    }*/

//   @GetMapping("/main_login")
//   public String main_login() {
//       return "main_login";
//   }

//   @GetMapping("/#")
//   public String callback() {
//       return "main";
//   }

//   @GetMapping("/login")
//    public String login() { return "main"; }

}

package com.capstone.server.controller;

import com.capstone.server.user.CompareVO;
import com.capstone.server.user.UserSubmitVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class CompareController {

    @RequestMapping(value = "/compare", method = RequestMethod.GET)
    public String compare(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "compare";
    }

    @RequestMapping(value = "/compare_result", method = RequestMethod.POST)
    public String compare_result_login(CompareVO compareVO, Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("company", compareVO.getCompany());
        model.addAttribute("job", compareVO.getJob());

        



        return "compare_result";
    }

}

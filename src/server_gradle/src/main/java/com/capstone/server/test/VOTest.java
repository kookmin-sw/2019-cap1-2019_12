package com.capstone.server.test;

import com.capstone.server.user.UserSubmitVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class VOTest {

    @RequestMapping(value="/submit.do", method= RequestMethod.POST)
    public String doTest(UserSubmitVO userSubmitVO)
    {
        System.out.println("company : " + userSubmitVO.getCompany());
        System.out.println("type : " + userSubmitVO.getType());
        System.out.println("question : " + userSubmitVO.getQuestion());
        System.out.println("text : " + userSubmitVO.getText());

        return "result";
    }


}

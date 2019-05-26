package com.capstone.server.controller;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.capstone.server.user.UserSubmitVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;


@Controller
public class VOTest {

    private String companyToEng(String company) {

        switch (company) {
            case "현대":
                company = "hyundai";
                break;
            case "삼성":
                company = "samsung";
                break;
            case "SK":
                company = "sk";
                break;
            case "CJ":
                company = "cj";
                break;
            case "LG":
                company = "lg";
                break;
        }


        return company;
    }

    private String jobToEng(String job) {

        switch (job) {
            case "영업":
                job = "marketing";
                break;
            case "생산":
                job = "production";
                break;
            case "경영지원관리":
                job = "management";
                break;
            case "건설":
                job = "construct";
                break;
            case "IT":
                break;
        }

        return job;
    }

    @RequestMapping(value="/votest", method=RequestMethod.POST)
    public String doTest(UserSubmitVO userSubmitVO, Model model)
    {
        userSubmitVO.setCompany(companyToEng(userSubmitVO.getCompany()));
        userSubmitVO.setJob(jobToEng(userSubmitVO.getJob()));

//        System.out.println("company : " + userSubmitVO.getCompany());
//        System.out.println("job : " + userSubmitVO.getjob());
//        System.out.println("question : " + userSubmitVO.getQuestion());
//        System.out.println("text : " + userSubmitVO.getText());

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table tableCompany = dynamoDB.getTable("Company");
        Table tableJob = dynamoDB.getTable("Job");

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#data", "Data");


        GetItemSpec specCompany = new GetItemSpec()
                .withPrimaryKey("Company_Name", userSubmitVO.getCompany())
                .withProjectionExpression("#data")
                .withNameMap(nameMap);

        GetItemSpec specJob = new GetItemSpec()
                .withPrimaryKey("Job_Name", userSubmitVO.getJob())
                .withProjectionExpression("#data")
                .withNameMap(nameMap);


        Item item = tableCompany.getItem(specCompany);
        Map itemCompany = (Map) item.get("Data");

        System.out.println(item.toJSONPretty());
        System.out.println(itemCompany);
        System.out.println(itemCompany.get("honesty"));

        item = tableJob.getItem(specJob);

        System.out.println(item.toJSONPretty());

        model.addAttribute("company", userSubmitVO.getCompany().toUpperCase());
        model.addAttribute("job", userSubmitVO.getJob());

//        userSubmitVO.setCompany(userSubmitVO.getCompany().toUpperCase());

        model.addAttribute("active", itemCompany.get("active"));
        model.addAttribute("challenge", itemCompany.get("challenge"));
        model.addAttribute("communication", itemCompany.get("communication"));
        model.addAttribute("creative", itemCompany.get("creative"));
        model.addAttribute("global", itemCompany.get("global"));
        model.addAttribute("honesty", itemCompany.get("honesty"));
        model.addAttribute("patient", itemCompany.get("patient"));
        model.addAttribute("responsibility", itemCompany.get("responsibility"));
        model.addAttribute("sincerity", itemCompany.get("sincerity"));
        model.addAttribute("teamwork", itemCompany.get("teamwork"));


        return "result";
    }

}

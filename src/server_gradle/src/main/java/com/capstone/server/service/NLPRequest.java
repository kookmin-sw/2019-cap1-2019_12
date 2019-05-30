package com.capstone.server.service;

import com.capstone.server.user.UserSubmitVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NLPRequest {

    public static String main(UserSubmitVO userSubmitVO) throws IOException, ParseException {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(50000);
        factory.setConnectTimeout(3000);
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(100)
                .setMaxConnPerRoute(5)
                .build();
        factory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
        String url = "http://15.164.19.93:5000/";
//        String url = "http://127.0.0.1:5000/";




        MultiValueMap<String, String> test = new LinkedMultiValueMap<String, String>();

        String question;
        String text;
        userSubmitVO.companyToUpper();
        test.add("company", userSubmitVO.getCompany());
        test.add("q_num", userSubmitVO.getQ_num().toString());
        for(int i=1; i<=userSubmitVO.getQ_num();i++)
        {
            question = "q" + i;
            text = userSubmitVO.getText(i-1).replaceAll(System.getProperty("line.separator"), " ");
            test.add(question, text);
            System.out.println(text);
        }


//        q1 = q1.replaceAll(System.getProperty("line.separator"), "");
//        q2 = q2.replaceAll(System.getProperty("line.separator"), "");



//        test.add("q1", q1);
//        test.add("q2", q2);
//        test.add("q", userSubmitVO.getText());

        String obj;
        obj = restTemplate.postForObject(url, test ,String.class);

//        System.out.println(obj);

//        String obj2 = obj.replaceAll("\\\\", "");
//
//        System.out.println(obj);
//        System.out.println(obj2);
//        obj2 = obj2.substring(1, obj2.length()-2);

//        assert obj != null;
//        obj = obj.substring(1, obj.length()-2);
//        System.out.println(obj);

//        ObjectMapper mapper = new ObjectMapper();

//        JSONObject feedback = (JSONObject) new JSONParser().parse(obj2);
//
//
//        System.out.println(feedback);

        return obj;
//
//        Map<String, Object > map = new HashMap<String, Object>();
//        map = mapper.readValue(obj, new TypeReference<Map<String, Object>>()  {});
//
//
//
//        System.out.println(obj);


/*
        // URL 에 있는 JSON String 을 Map으로 변환하기
        Map<String, Object> data = mapper.readValue(
                new URL("https://gturnquist-quoters.cfapps.io/api/random"),
                new TypeReference<Map<String,Object>>(){});

        // {type=success, value={id=9, quote=So easy it is to switch container in #springboot.}}
        System.out.println(data);

        System.out.println(data.get("value"));

        // Map을 JSON String 으로 변환
        System.out.println(mapper.writeValueAsString(data));

        // Map을 보기쉬운 JSON String 으로 변환
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data));
        */




    }

}

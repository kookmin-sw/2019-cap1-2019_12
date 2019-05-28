package com.capstone.server.service;

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

    public static JSONObject main() throws IOException, ParseException {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(50000);
        factory.setConnectTimeout(3000);
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(100)
                .setMaxConnPerRoute(5)
                .build();
        factory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
        String url = "http://13.209.217.136:5000/";

        MultiValueMap<String, String> test = new LinkedMultiValueMap<String, String>();
        test.add("test", "1. LG유플러스 및 분야/직무에 대한 지원 동기와 해당 분야/직무를 잘 수행하기 위해 어떤 준비를 해왔는지 구체적으로 설명해주세요. [책임감을 갖고 역량을 펼칠 수 있는 곳] LG유플러스는 제가 갖춘 능력을 활용하여 새로운 가치를 창출할 수 있는 곳이라고 생각합니다. 현재 LG유플러스는 우리나라의 통신업을 이끌어 가고 있습니다. 통신업을 이끌어가는 선두주자로서 4차 산업혁명의 중심에 서 있기도 합니다. 소비자와 직접 연관이 있는 스마트폰, 인터넷과 같은 사업에 지속적인 투자를 통해 미래시장에서 우위를 점하려고 노력하고 있기도 합니다. 또한, 세상의 변화에 기민하게 대처하여 우리가 생각해 본 적 없는 방식의 서비스를 제공하려 하고 있는 것으로 알고있습니다. 저는 이러한 LG유플러스의 신념과 동행하여 함께 성장할 수 있다고 생각하여 지원하게 되었습니다. [신뢰받는 엔지니어가 되겠습니다] NW운영 부서는 고객과의 지속적인 커뮤니케이션을 통해 네트워크 운영에서 발생할 수 있는 이슈를 예방하고 해결해야 합니다. 또한, 이슈 발생 시 정해진 시간 내에 최고의 방법을 도출하여 손실을 최소화해야 하므로, 엔지니어로서 필요한 역량은 협업 능력, 문제해결능력이라고 생각합니다. 저는 재학시절 다수의 프로젝트 경험과 회사생활을 하면서 직무역량을 향상하고자 노력했습니다. 첫째, 연구실 생활과 팀 프로젝트를 통해 협업능력을 기를 수 있었습니다. 짧게는 2주, 길게는 6개월 동안 프로젝트를 진행하면서 커뮤니케이션 역량을 발휘하여 팀원에게 신뢰를 얻은 경험이 있습니다. 이 경험을 바탕으로, 실제 제품을 제작하여 전기학회에 출품하였고, 특허도 등록하여 좋은 결과를 얻을 수 있었습니다. 둘째, 문제해결능력입니다. 짧지만, 전력회사에서 근무하면서 개발 프로세스를 배웠고, 여러 부서와 부딪히며 업무를 수행했습니다. 업무를 수행하면서 장비의 결함, 협력사와의 갈등 등이 있었지만, 팀원들과 함께 해결하여 끝까지 완수할 수 있었습니다. 이러한 역량을 바탕으로 불필요한 손실 없이 문제를 해결하여 LG유플러스의 이익증대 기여에 노력하겠습니다.");

        String obj;
        obj = restTemplate.postForObject(url, test ,String.class);

        System.out.println(obj);

        String obj2 = obj.replaceAll("\\\\", "");

        System.out.println(obj);
        System.out.println(obj2);
        obj2 = obj2.substring(1, obj2.length()-2);

//        assert obj != null;
//        obj = obj.substring(1, obj.length()-2);
//        System.out.println(obj);

//        ObjectMapper mapper = new ObjectMapper();

        JSONObject feedback = (JSONObject) new JSONParser().parse(obj2);


        System.out.println(feedback);

        return feedback;
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

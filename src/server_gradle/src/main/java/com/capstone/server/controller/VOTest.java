package com.capstone.server.controller;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.capstone.server.dbtest.Resttest;
import com.capstone.server.service.NLPRequest;
import com.capstone.server.user.UserSubmitVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

import static java.lang.Float.parseFloat;
import static jdk.nashorn.internal.objects.NativeFunction.function;


@Controller
public class VOTest {

    @RequestMapping(value="/votest", method=RequestMethod.POST)
    public String doTest(UserSubmitVO userSubmitVO, Model model, Principal principal) throws IOException, ParseException {
        model.addAttribute("company", userSubmitVO.getCompany());
        model.addAttribute("job", userSubmitVO.getJob());

        model.addAttribute("username", principal.getName());

        userSubmitVO.companyToEng();
        userSubmitVO.jobToEng();


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
                .withProjectionExpression("#data, Main, talent")
                .withNameMap(nameMap);


        GetItemSpec specJob = new GetItemSpec()
                .withPrimaryKey("Job_Name", userSubmitVO.getJob())
                .withProjectionExpression("#data, talent")
                .withNameMap(nameMap);

        Item item = tableCompany.getItem(specCompany);
        Map itemCompany = (Map) item.get("Data");
        String talentCompany = (String) item.get("talent");

//        System.out.println(item.toJSONPretty());
//        System.out.println(itemCompany);
//        System.out.println(itemCompany.get("honesty"));

        item = tableJob.getItem(specJob);
        Map itemJob = (Map) item.get("Data");
        String talentJob = (String) item.get("talent");

        System.out.println(item.toJSONPretty());



//        userSubmitVO.setCompany(userSubmitVO.getCompany().toUpperCase());

//        선택 기업 데이터

        model.addAttribute("talent_Company", talentCompany);

        model.addAttribute("active",            itemCompany.get("active"));
        model.addAttribute("challenge",         itemCompany.get("challenge"));
        model.addAttribute("communication",     itemCompany.get("communication"));
        model.addAttribute("creative",          itemCompany.get("creative"));
        model.addAttribute("global",            itemCompany.get("global"));
        model.addAttribute("honesty",           itemCompany.get("honesty"));
        model.addAttribute("patient",           itemCompany.get("patient"));
        model.addAttribute("responsibility",    itemCompany.get("responsibility"));
        model.addAttribute("sincerity",         itemCompany.get("sincerity"));
        model.addAttribute("teamwork",          itemCompany.get("teamwork"));

//        선택 직무 데이터

        System.out.println(talentJob);
        model.addAttribute("talent_Job", talentJob);

        model.addAttribute("active_Job",            itemJob.get("active"));
        model.addAttribute("challenge_Job",         itemJob.get("challenge"));
        model.addAttribute("communication_Job",     itemJob.get("communication"));
        model.addAttribute("creative_Job",          itemJob.get("creative"));
        model.addAttribute("global_Job",            itemJob.get("global"));
        model.addAttribute("honesty_Job",           itemJob.get("honesty"));
        model.addAttribute("patient_Job",           itemJob.get("patient"));
        model.addAttribute("responsibility_Job",    itemJob.get("responsibility"));
        model.addAttribute("sincerity_Job",         itemJob.get("sincerity"));
        model.addAttribute("teamwork_Job",          itemJob.get("teamwork"));

//        JSONObject user = NLPRequest.main();

//        구현용 teststring을 이용한 매핑

        String testresult = "{\n" +
                "  \"user\": {\n" +
                "    \"global\": \"42.7754194822695110733690171401\",\n" +
                "    \"active\": \"47.163446436762249902585608651862\",\n" +
                "    \"challenge\": \"12.902232102841789895819601952098\",\n" +
                "    \"sincerity\": \"7.537250181366866641496926604304\",\n" +
                "    \"communication\": \"27.252393582106538616471880231984\",\n" +
                "    \"patient\": \"12.42384842161574631802523072110\",\n" +
                "    \"honesty\": \"83.9286657178670343348869665\",\n" +
                "    \"responsibility\": \"11.5360158672077398045452412134\",\n" +
                "    \"creative\": \"32.42753112501177259474616221269\",\n" +
                "    \"teamwork\": \"0.012716102107846224966714032689\"\n" +
                "  },\n" +
                "  \"Holland\": {\n" +
                "    \"S\": \"9.230954626345694080669090908486\",\n" +
                "    \"E\": \"15.855947952694323888067629013676\",\n" +
                "    \"C\": \"8.883344310185620429365371819586\"\n" +
                "  },\n" +
                "  \"company\": {\n" +
                "    \"CJ\": \"71\",\n" +
                "    \"hyundai\": \"91\",\n" +
                "    \"SK\": \"94\",\n" +
                "    \"samsung\": \"97\",\n" +
                "    \"LG\": \"10\"\n" +
                "  },\n" +
                "  \"choice_company\": {\n" +
                "    \"global\": \"15.120075545649813264503791288007\",\n" +
                "    \"active\": \"131.651558648623222325113601982594\",\n" +
                "    \"challenge\": \"101.487942076313657935315859504044\",\n" +
                "    \"sincerity\": \"25.043280813300039966406984603964\",\n" +
                "    \"communication\": \"49.057913638720151539018843322992\",\n" +
                "    \"patient\": \"24.861037275833705706418186309747\",\n" +
                "    \"honesty\": \"4.472471820222674310230104310904\",\n" +
                "    \"responsibility\": \"10.126530495645172713636839034734\",\n" +
                "    \"creative\": \"67.353443958855763185056275688112\",\n" +
                "    \"teamwork\": \"3.244215665280616722743189939138\"\n" +
                "  },\n" +
                "  \"first_company\": {\n" +
                "    \"global\": \"11.459025573741156378559935546946\",\n" +
                "    \"active\": \"21.529810615670271545241121202707\",\n" +
                "    \"challenge\": \"104.883226251792692096387327183038\",\n" +
                "    \"sincerity\": \"25.068460139101777173209484317340\",\n" +
                "    \"communication\": \"462.384366674848138245579320937395\",\n" +
                "    \"patient\": \"29.154970559822036335617667646147\",\n" +
                "    \"honesty\": \"4.496185592909177408671439479804\",\n" +
                "    \"responsibility\": \"1.047610343540322341837622843741\",\n" +
                "    \"creative\": \"69.992633540079012277601577807218\",\n" +
                "    \"teamwork\": \"29.5138039238148575105213922143\"\n" +
                "  }\n" +
                "}";

        JSONObject user = (JSONObject) new JSONParser().parse(testresult);

        JSONObject userUser = (JSONObject) user.get("user");
        JSONObject userHolland = (JSONObject) user.get("Holland");
        JSONObject userCompany = (JSONObject) user.get("company");
        JSONObject userChoicecompany = (JSONObject) user.get("choice_company");
        JSONObject userFirstcompany = (JSONObject) user.get("first_company");

        JSONArray ja = new JSONArray();
        JSONObject sk = new JSONObject();
        JSONObject lg = new JSONObject();
        JSONObject cj = new JSONObject();
        JSONObject hyundai = new JSONObject();
        JSONObject samsung = new JSONObject();
        sk.put("SK", userCompany.get("SK"));
        lg.put("LG", userCompany.get("LG"));
        cj.put("CJ", userCompany.get("CJ"));
        hyundai.put("hyundai", userCompany.get("hyundai"));
        samsung.put("samsung", userCompany.get("samsung"));
        ja.add(sk.get("SK"));
        ja.add(lg.get("LG"));
        ja.add(cj.get("CJ"));
        ja.add(hyundai.get("hyundai"));
        ja.add(samsung.get("samsung"));

//        System.out.println(ja.get(0));
//        System.out.println(ja.get(1));
//        System.out.println(ja.get(2));
//        System.out.println(ja.get(3));
//        System.out.println(ja.get(4));


        ArrayList<String> stringList = new ArrayList<String>();

        stringList.add((String) userCompany.get("SK"));
        stringList.add((String) userCompany.get("LG"));
        stringList.add((String) userCompany.get("CJ"));
        stringList.add((String) userCompany.get("samsung"));
        stringList.add((String) userCompany.get("hyundai"));

        System.out.println("정렬전");
        System.out.println(stringList);

        System.out.println("정렬후");
        Collections.sort(stringList, new AscendingString());
        System.out.println(stringList);


        ArrayList<String> n = new ArrayList<String>();
        String n1;
        String n2;
        String n3;

        for(int i=0;i<5;i++){
            if(stringList.get(i) == userCompany.get("SK")) n.add("SK");
            else if (stringList.get(i) == userCompany.get("LG")) n.add("LG");
            else if (stringList.get(i) == userCompany.get("CJ")) n.add("CJ");
            else if (stringList.get(i) == userCompany.get("hyundai")) n.add("현대");
            else if (stringList.get(i) == userCompany.get("samsung")) n.add("삼성");
        }
        System.out.println(n);
//        for(int i=0;i<5;i++){
//            if(stringList.get(i) == userCompany.get("SK")) n1 = "SK";
//            else if (stringList.get(i) == userCompany.get("LG")) n1 = "LG";
//            else if (stringList.get(i) == userCompany.get("CJ")) n1 = "CJ";
//            else if (stringList.get(i) == userCompany.get("hyundai")) n1 = "LG";
//            else if (stringList.get(i) == userCompany.get("LG")) n1 = "LG";
//        }

        model.addAttribute("n1_company", n.get(0));
        model.addAttribute("n2_company", n.get(1));
        model.addAttribute("n3_company", n.get(2));

        model.addAttribute("n1_value", stringList.get(0));
        model.addAttribute("n2_value", stringList.get(1));
        model.addAttribute("n3_value", stringList.get(2));




//        ja.get(0);

//        System.out.println(user);
//        System.out.println(userUser);
//        System.out.println(userUser.get("teamwork"));

//        홀랜드 유형
        String holland = "S";
        String holland_text = "사회형은 다른 사람들과 함께 협력하여 일하는 것을 좋아하고 어려운 처지에 놓인 사람들의 복지에 관심이 있습니다.<span style=\"line-height:200%\"></span><br>" +
                "또 남을 돌보고 자신이 알고 있는 것을 다른 사람들에게 교육하는 일에 관심이 많습니다.<span style=\"line-height:200%\"></span><br>" +
                "다른 사람들과 적극적으로 대화하고 토론하는 것을 즐기며 사람들 간에 얽힌 문제를 풀어 주는 일에 앞장섭니다.<span style=\"line-height:200%\"></span><br>" +
                "열심히 일한 만큼 인정받기를 원하고 대가를 바라지 않고 봉사하기를 좋아합니다.";
        Float S = parseFloat((String) userHolland.get("S"));
        Float E = parseFloat((String) userHolland.get("E"));
        Float C = parseFloat((String) userHolland.get("C"));

        if(E > S){
            holland = "E";
            holland_text = "기업형은 진취적인 자세로 다른 사람들을 이끌고 목표를 달성하려는 강한 의지를 보여 주는 유형입니다.<span style=\"line-height:200%\"></span><br>" +
                    "벌어지고 있는 상황을 빨리 파악하고 문제점을 찾아내어 신속하게 해결하려고 합니다.<span style=\"line-height:200%\"></span><br>" +
                    "일이 되게끔 조정하고 결단을 내리는 일에 익숙합니다.<span style=\"line-height:200%\"></span><br>" +
                    "남보다 앞서 나가기를 좋아하고 직장이나 단체에서도 일을 책임지고 결정짓는 위치에 올라가려는 욕구가 강하지요.<span style=\"line-height:200%\"></span><br>" +
                    "목표를 정할 때는 달성이 가능하고 대가가 분명한 일을 정하고 목표를 이루기 위해 적극적으로 나섭니다.";
            if(C > E){
                holland = "C";
                holland_text = "사무형은 사무실에서 일하기 좋아하는 유형이 아닙니다. 번역자에 따라서 관습형으로 표현하기도 합니다.<span style=\"line-height:200%\"></span><br>" +
                        "이 유형은 잘 짜인 조직이나 틀 안에서 일하기 좋아하고 잘합니다.<span style=\"line-height:200%\"></span><br>" +
                        "일을 할 때에도 일의 목표와 절차, 수단이 명백하게 제시되기를 바랍니다.<span style=\"line-height:200%\"></span><br>" +
                        "조직 안에서 자신에게 기대하고 있는 것이 무엇이고 자신이 해야 할 일이 무엇인지 분명하게 알기를 원하지요.<span style=\"line-height:200%\"></span><br>" +
                        "이 유형은 조직의 체계와 자료 내용, 처리해야 할 세부사항, 정확성에 관심을 둡니다.<span style=\"line-height:200%\"></span><br>" +
                        "체계적이고 질서정연한 조직에서 일하기를 좋아하지만 지위나 권력에 관심이 있다기보다 일 자체의 능률과 효율성에<span style=\"line-height:200%\"></span><br>" +
                        "관심이 있습니다. 한 번도 하지 않았던 일이나 스스로 알아서 창의적으로 해야 하는 작업을 힘들어할 수 있습니다.";
            }
        }
        else if(C > S){
            holland = "C";
            holland_text = "사무형은 사무실에서 일하기 좋아하는 유형이 아닙니다. 번역자에 따라서 관습형으로 표현하기도 합니다.<span style=\"line-height:200%\"></span><br>" +
                    "이 유형은 잘 짜인 조직이나 틀 안에서 일하기 좋아하고 잘합니다.<span style=\"line-height:200%\"></span><br>" +
                    "일을 할 때에도 일의 목표와 절차, 수단이 명백하게 제시되기를 바랍니다.<span style=\"line-height:200%\"></span><br>" +
                    "조직 안에서 자신에게 기대하고 있는 것이 무엇이고 자신이 해야 할 일이 무엇인지 분명하게 알기를 원하지요.<span style=\"line-height:200%\"></span><br>" +
                    "이 유형은 조직의 체계와 자료 내용, 처리해야 할 세부사항, 정확성에 관심을 둡니다.<span style=\"line-height:200%\"></span><br>" +
                    "체계적이고 질서정연한 조직에서 일하기를 좋아하지만 지위나 권력에 관심이 있다기보다 일 자체의 능률과 효율성에<span style=\"line-height:200%\"></span><br>" +
                    "관심이 있습니다. 한 번도 하지 않았던 일이나 스스로 알아서 창의적으로 해야 하는 작업을 힘들어할 수 있습니다.";
        }

        model.addAttribute("holland", holland);
        model.addAttribute("holland_text", holland_text);
//        추천 기업 순위



//        직무

        model.addAttribute("user_text", userUser.get("text"));

        model.addAttribute("active_user",            userUser.get("active"));
        model.addAttribute("challenge_user",         userUser.get("challenge"));
        model.addAttribute("communication_user",     userUser.get("communication"));
        model.addAttribute("creative_user",          userUser.get("creative"));
        model.addAttribute("global_user",            userUser.get("global"));
        model.addAttribute("honesty_user",           userUser.get("honesty"));
        model.addAttribute("patient_user",           userUser.get("patient"));
        model.addAttribute("responsibility_user",    userUser.get("responsibility"));
        model.addAttribute("sincerity_user",         userUser.get("sincerity"));
        model.addAttribute("teamwork_user",          userUser.get("teamwork"));

//        선택 기업
        model.addAttribute("active_choice_company",            userChoicecompany.get("active"));
        model.addAttribute("challenge_choice_company",         userChoicecompany.get("challenge"));
        model.addAttribute("communication_choice_company",     userChoicecompany.get("communication"));
        model.addAttribute("creative_choice_company",          userChoicecompany.get("creative"));
        model.addAttribute("global_choice_company",            userChoicecompany.get("global"));
        model.addAttribute("honesty_choice_company",           userChoicecompany.get("honesty"));
        model.addAttribute("patient_choice_company",           userChoicecompany.get("patient"));
        model.addAttribute("responsibility_choice_company",    userChoicecompany.get("responsibility"));
        model.addAttribute("sincerity_choice_company",         userChoicecompany.get("sincerity"));
        model.addAttribute("teamwork_choice_company",          userChoicecompany.get("teamwork"));


//        1순위 기업
        model.addAttribute("active_first_company",            userFirstcompany.get("active"));
        model.addAttribute("challenge_first_company",         userFirstcompany.get("challenge"));
        model.addAttribute("communication_first_company",     userFirstcompany.get("communication"));
        model.addAttribute("creative_first_company",          userFirstcompany.get("creative"));
        model.addAttribute("global_first_company",            userFirstcompany.get("global"));
        model.addAttribute("honesty_first_company",           userFirstcompany.get("honesty"));
        model.addAttribute("patient_first_company",           userFirstcompany.get("patient"));
        model.addAttribute("responsibility_first_company",    userFirstcompany.get("responsibility"));
        model.addAttribute("sincerity_first_company",         userFirstcompany.get("sincerity"));
        model.addAttribute("teamwork_first_company",          userFirstcompany.get("teamwork"));



        return "result";
    }


    class AscendingString implements Comparator<String>{

        @Override
        public int compare(String a, String b){
            return b.compareTo(a);
        }
    }
}

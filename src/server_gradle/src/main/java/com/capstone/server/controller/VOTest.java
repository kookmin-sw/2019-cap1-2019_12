package com.capstone.server.controller;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.capstone.server.service.NLPRequest;
import com.capstone.server.user.UserSubmitVO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Float.parseFloat;

@Controller
public class VOTest {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    @RequestMapping(value="/votest", method=RequestMethod.POST)
    public String doTest(UserSubmitVO userSubmitVO, Model model, Principal principal) throws IOException, ParseException {
        model.addAttribute("company", userSubmitVO.getCompany());
        model.addAttribute("job", userSubmitVO.getJob());

        model.addAttribute("username", principal.getName());

        userSubmitVO.companyToEng();
        userSubmitVO.jobToEng();

        System.out.println(userSubmitVO.getQ_num());

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

//        System.out.println(item.toJSONPretty());



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

//        System.out.println(talentJob);
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

//        System.out.println(userSubmitVO.getCompany());

        String testresult = NLPRequest.main(userSubmitVO);

        JSONObject val = (JSONObject) new JSONParser().parse(testresult);

        Table table = dynamoDB.getTable("compare");

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String st = sdf.format(timestamp);

        String nst = st.replace(".","");
        Long num_timestamp = Long.parseLong(nst);
        //System.out.println(nst);

        Item itemuser = new Item()
                .withPrimaryKey("timestamp",st)
                .withNumber("numstamp", num_timestamp)
                .withJSON("Data", testresult)
                .withString("email", "test1234")
                .withString("title",userSubmitVO.getTitle())
                .withString("select_job",userSubmitVO.getJob())
                .withString("select_company",userSubmitVO.getCompany());

        PutItemOutcome poutcome = table.putItem(itemuser);

        JSONObject user = (JSONObject) new JSONParser().parse(testresult);



        JSONObject userUser = (JSONObject) user.get("user");
        JSONObject userHolland = (JSONObject) user.get("Holland");
        JSONObject userCompany = (JSONObject) user.get("company");
        JSONObject userChoicecompany = (JSONObject) user.get("choice_company");
//        JSONObject userFirstcompany = (JSONObject) user.get("first_company");

//        문항별 분석 결과

//        List<JSONObject> userQ = new ArrayList<>();

        for(int i=1;i<userSubmitVO.getQ_num()+1;i++)
        {
//            userQ.add((JSONObject) user.get("q"+ i));

            JSONObject userQ = (JSONObject) user.get("q"+i);

            String question = userSubmitVO.getQuestion(i-1);

            model.addAttribute("question"+i, question);

            model.addAttribute("active_q"+i,            userQ.get("active"));
            model.addAttribute("challenge_q"+i,         userQ.get("challenge"));
            model.addAttribute("communication_q"+i,     userQ.get("communication"));
            model.addAttribute("creative_q"+i,          userQ.get("creative"));
            model.addAttribute("global_q"+i,            userQ.get("global"));
            model.addAttribute("honesty_q"+i,           userQ.get("honesty"));
            model.addAttribute("patient_q"+i,           userQ.get("patient"));
            model.addAttribute("responsibility_q"+i,    userQ.get("responsibility"));
            model.addAttribute("sincerity_q"+i,         userQ.get("sincerity"));
            model.addAttribute("teamwork_q"+i,          userQ.get("teamwork"));

        }

        JSONObject userQ1 = (JSONObject) user.get("q1");

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



//        직무 분석 결과

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

//        선택 기업 분석 결과
        model.addAttribute("active_choice_company",            userUser.get("active"));
        model.addAttribute("challenge_choice_company",         userUser.get("challenge"));
        model.addAttribute("communication_choice_company",     userUser.get("communication"));
        model.addAttribute("creative_choice_company",          userUser.get("creative"));
        model.addAttribute("global_choice_company",            userUser.get("global"));
        model.addAttribute("honesty_choice_company",           userUser.get("honesty"));
        model.addAttribute("patient_choice_company",           userUser.get("patient"));
        model.addAttribute("responsibility_choice_company",    userUser.get("responsibility"));
        model.addAttribute("sincerity_choice_company",         userUser.get("sincerity"));
        model.addAttribute("teamwork_choice_company",          userUser.get("teamwork"));


//        1순위 기업
        ArrayList<Float> stringList = new ArrayList<Float>();


        if(userCompany.containsKey("SK")) stringList.add(Float.parseFloat((String) userCompany.get("SK")));
        if(userCompany.containsKey("LG")) stringList.add(Float.parseFloat((String) userCompany.get("LG")));
        if(userCompany.containsKey("CJ")) stringList.add(Float.parseFloat((String) userCompany.get("CJ")));
        if(userCompany.containsKey("samsung")) stringList.add(Float.parseFloat((String) userCompany.get("samsung")));
        if(userCompany.containsKey("hyundai")) stringList.add(Float.parseFloat((String) userCompany.get("hyundai")));

//        System.out.println("정렬전");
//        System.out.println(userCompany);
//        System.out.println(stringList);

//        System.out.println("정렬후");
        Collections.sort(stringList, new AscendingString());
//        System.out.println(stringList);

        ArrayList<String> n = new ArrayList<String>();



        for(int i=0;i<3;i++){
//            System.out.println("test");

            if(userCompany.containsKey("SK"))
            {
                if(stringList.get(i) == Float.parseFloat(getUC(userCompany, "SK"))) n.add("SK");
            }
            if(userCompany.containsKey("LG"))
            {
                if(stringList.get(i) == Float.parseFloat(getUC(userCompany, "LG"))) n.add("LG");
            }
            if(userCompany.containsKey("CJ"))
            {
                if(stringList.get(i) == Float.parseFloat(getUC(userCompany, "CJ"))) n.add("CJ");
            }
            if(userCompany.containsKey("hyundai"))
            {
                if(stringList.get(i) == Float.parseFloat(getUC(userCompany, "hyundai"))) n.add("현대");
            }
            if(userCompany.containsKey("samsung"))
            {
                if(stringList.get(i) == Float.parseFloat(getUC(userCompany, "samsung"))) n.add("삼성");
            }

//            if(stringList.get(i) == Float.parseFloat((String) userCompany.get("SK"))) n.add("SK");
//            else if (stringList.get(i) == Float.parseFloat((String) userCompany.get("LG"))) n.add("LG");
//            else if (stringList.get(i) == Float.parseFloat((String) userCompany.get("CJ"))) n.add("CJ");
//            else if (stringList.get(i) == Float.parseFloat((String) userCompany.get("현대"))) n.add("현대");
//            else if (stringList.get(i) == Float.parseFloat((String) userCompany.get("삼성"))) n.add("삼성");
        }




//        System.out.println(n);

        model.addAttribute("n1_company", n.get(0));
        model.addAttribute("n2_company", n.get(1));
        model.addAttribute("n3_company", n.get(2));

        model.addAttribute("n1_value", stringList.get(0));
        model.addAttribute("n2_value", stringList.get(1));
        model.addAttribute("n3_value", stringList.get(2));

//        System.out.println(n);

        if(n.get(0) == "삼성") n.set(0, "samsung");
        else if(n.get(0) == "현대") n.set(0, "hyundai");


        GetItemSpec specFirstCompany = new GetItemSpec()
                .withPrimaryKey("Company_Name", n.get(0).toLowerCase())
                .withProjectionExpression("#data")
                .withNameMap(nameMap);

        Item itemFirst = tableCompany.getItem(specFirstCompany);
        Map itemFirstCompany = (Map) itemFirst.get("Data");

        JSONObject userFirstcompany = (JSONObject) user.get("first_company");

        model.addAttribute("active_first_company",            itemFirstCompany.get("active"));
        model.addAttribute("challenge_first_company",         itemFirstCompany.get("challenge"));
        model.addAttribute("communication_first_company",     itemFirstCompany.get("communication"));
        model.addAttribute("creative_first_company",          itemFirstCompany.get("creative"));
        model.addAttribute("global_first_company",            itemFirstCompany.get("global"));
        model.addAttribute("honesty_first_company",           itemFirstCompany.get("honesty"));
        model.addAttribute("patient_first_company",           itemFirstCompany.get("patient"));
        model.addAttribute("responsibility_first_company",    itemFirstCompany.get("responsibility"));
        model.addAttribute("sincerity_first_company",         itemFirstCompany.get("sincerity"));
        model.addAttribute("teamwork_first_company",          itemFirstCompany.get("teamwork"));



        return "result"+userSubmitVO.getQ_num();
    }

    public String getUC(JSONObject jo,String c) {
        return jo.get(c).toString();
    }

    class AscendingString implements Comparator<Float>{

        @Override
        public int compare(Float a, Float b){
            return b.compareTo(a);
        }
    }
}

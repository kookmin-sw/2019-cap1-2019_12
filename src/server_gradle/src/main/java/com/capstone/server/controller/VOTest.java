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
//        System.out.println(testresult);
//        구현용 teststring을 이용한 매핑

//        String testresult = "{\"q1\": {\"global\": \"11.220942645283054517335585842375\", \"active\": \"8.904465996769065583293922827579\", \"challenge\": \"13.208342738930273441155804903246\", \"sincerity\": \"5.337106970312838427616952685639\", \"communication\": \"4.163658120678038088158245955128\", \"patient\": \"9.514154330526663372324946976732\", \"honesty\": \"4.065221600028484871813816425856\", \"responsibility\": \"32.292325595482594735585735179484\", \"creative\": \"5.892846762614977862426712817978\", \"teamwork\": \"5.400935239374012653001955186483\"}, \"q2\": {\"global\": \"9.334742987078794129729431006126\", \"active\": \"11.420470091807583656873248401098\", \"challenge\": \"16.755695676136280525270194630139\", \"sincerity\": \"15.150377289149286497149660135619\", \"communication\": \"3.198520117320909239566617543460\", \"patient\": \"7.355788778704646091455288114958\", \"honesty\": \"0.638206629596759777989234407869\", \"responsibility\": \"32.878466245843860349395981756970\", \"creative\": \"1.311919507879319990450994737330\", \"teamwork\": \"1.955812676482548306822195627319\"}, \"user\": {\"global\": \"8.977392725945424700739749823697\", \"active\": \"10.638922046513018315749832254369\", \"challenge\": \"15.970610983611804201132144953590\", \"sincerity\": \"8.616903181794546284777425171342\", \"communication\": \"3.658214994764242611324789322680\", \"patient\": \"6.090478175187458376171889540274\", \"honesty\": \"1.067237973765372327505929206382\", \"responsibility\": \"40.721437684325607619939546566457\", \"creative\": \"1.762871071533821387689044968283\", \"teamwork\": \"2.495931162558703508835833417834\"}, \"Holland\": {\"S\": \"5.043846294422790421663194138091\", \"E\": \"17.273460446496063269705700804479\", \"C\": \"5.258206443582459144181484589353\"}, \"company\": {\"samsung\": \"71.0\", \"hyundai\": \"20.0\", \"LG\": \"7.0\"}, \"choice_company\": {\"global\": \"96.650858111027503127843374386430\", \"active\": \"80.336793626358883102511754259467\", \"challenge\": \"116.191549564289658746929490007460\", \"sincerity\": \"40.620663472923062897734780563042\", \"communication\": \"53.128554902030074913454882334918\", \"patient\": \"78.197482510785121689877996686846\", \"honesty\": \"60.088878703572959238954354077578\", \"responsibility\": \"235.497398310155375611429917626083\", \"creative\": \"48.031493077668521607392904115841\", \"teamwork\": \"48.956529532795570958114694803953\"}, \"first_company\": {\"global\": \"81.966892247107765001601364929229\", \"active\": \"80.238008587340033272994332946837\", \"challenge\": \"129.536753764527475141221657395363\", \"sincerity\": \"40.436913069442312007595319300890\", \"communication\": \"38.465776721331643273060763021931\", \"patient\": \"100.852979908442264900259033311158\", \"honesty\": \"46.014866074227903425253316527233\", \"responsibility\": \"265.554388368225943395373178645968\", \"creative\": \"50.742923579938214118101313943043\", \"teamwork\": \"45.607917916616401043938822112978\"}}";

//        String testresult = "{\n" +
//                "  \"user\": {\n" +
//                "    \"global\": \"42.77\",\n" +
//                "    \"active\": \"47.16\",\n" +
//                "    \"challenge\": \"12.52\",\n" +
//                "    \"sincerity\": \"7.56\",\n" +
//                "    \"communication\": \"27.12\",\n" +
//                "    \"patient\": \"12.12\",\n" +
//                "    \"honesty\": \"83.12\",\n" +
//                "    \"responsibility\": \"11.12\",\n" +
//                "    \"creative\": \"32.13\",\n" +
//                "    \"teamwork\": \"0.41\"\n" +
//                "  },\n" +
//                "  \"Holland\": {\n" +
//                "    \"S\": \"9.230954626345694080669090908486\",\n" +
//                "    \"E\": \"15.855947952694323888067629013676\",\n" +
//                "    \"C\": \"30.883344310185620429365371819586\"\n" +
//                "  },\n" +
//                "  \"company\": {\n" +
//                "    \"CJ\": \"71\",\n" +
//                "    \"hyundai\": \"91\",\n" +
//                "    \"SK\": \"98\",\n" +
//                "    \"samsung\": \"97\",\n" +
//                "    \"LG\": \"10\"\n" +
//                "  },\n" +
//                "  \"choice_company\": {\n" +
//                "    \"global\": \"15.120075545649813264503791288007\",\n" +
//                "    \"active\": \"131.651558648623222325113601982594\",\n" +
//                "    \"challenge\": \"101.487942076313657935315859504044\",\n" +
//                "    \"sincerity\": \"25.043280813300039966406984603964\",\n" +
//                "    \"communication\": \"49.057913638720151539018843322992\",\n" +
//                "    \"patient\": \"24.861037275833705706418186309747\",\n" +
//                "    \"honesty\": \"4.472471820222674310230104310904\",\n" +
//                "    \"responsibility\": \"10.126530495645172713636839034734\",\n" +
//                "    \"creative\": \"67.353443958855763185056275688112\",\n" +
//                "    \"teamwork\": \"3.244215665280616722743189939138\"\n" +
//                "  },\n" +
//                "  \"first_company\": {\n" +
//                "    \"global\": \"11.459025573741156378559935546946\",\n" +
//                "    \"active\": \"21.529810615670271545241121202707\",\n" +
//                "    \"challenge\": \"104.883226251792692096387327183038\",\n" +
//                "    \"sincerity\": \"25.068460139101777173209484317340\",\n" +
//                "    \"communication\": \"462.384366674848138245579320937395\",\n" +
//                "    \"patient\": \"29.154970559822036335617667646147\",\n" +
//                "    \"honesty\": \"4.496185592909177408671439479804\",\n" +
//                "    \"responsibility\": \"1.047610343540322341837622843741\",\n" +
//                "    \"creative\": \"69.992633540079012277601577807218\",\n" +
//                "    \"teamwork\": \"29.5138039238148575105213922143\"\n" +
//                "  }\n" +
//                "}";

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

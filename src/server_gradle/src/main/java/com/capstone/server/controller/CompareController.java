package com.capstone.server.controller;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.capstone.server.user.UserSubmitVO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.*;

@Controller
public class CompareController {

    @RequestMapping(value = "/compare", method = RequestMethod.GET)
    public String compare(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "compare";
    }

    @RequestMapping(value = "/compare.getlist", method = RequestMethod.GET)
    public String comparegetlist(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());

//        DB 객체 생성
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("usertest");

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#ts", "timestamp");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":times", "2019.05.30.20.22.10");

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("#ts = :times")
                .withNameMap(nameMap)
                .withValueMap(valueMap)
//                .withValueMap(new ValueMap()
//                        .withString(":v_timestamp", "2019.05.30.20.22.10"))
                .withMaxPageSize(10)
                .withScanIndexForward(false);

        ItemCollection<QueryOutcome> items = table.query(spec);

        Iterator<Item> iterator = items.iterator();
        Item item = null;
        while (iterator.hasNext()) {
            item = iterator.next();
            System.out.println(item.toJSONPretty());
        }

//        HashMap<String, Object> valueMap = new HashMap<String, Object>();
//        valueMap.put(":id", "test1234");
//
//        ScanSpec scanSpec = new ScanSpec().withProjectionExpression("#ts, title,select_company,select_job")
//                .withFilterExpression("email = :id").withNameMap(nameMap)
//                .withValueMap(valueMap)
//
////        ItemCollection<ScanOutcome> items = table.scan(scanSpec);
//        ItemCollection<ScanOutcome> items = table.scan(scanSpec);
////        JSONObject item = items.;
//
//        Iterator<Item> iter = items.iterator();
//        while (iter.hasNext()) {
//            Item item = iter.next();
//            System.out.println(item.toString());
//        }

        return "compare";
    }

//    @RequestMapping(value = "/compare_result", method = RequestMethod.POST)
//    public String compare_result(Principal principal, Model model) {
//        model.addAttribute("username", principal.getName());
//        return "compare_result";
//    }

    @RequestMapping(value = "/compare_result", method = RequestMethod.POST)
    public String compare_result_login(UserSubmitVO userSubmitVO, Principal principal, Model model) throws ParseException {
//        String testString1 = "{\"q1\": {\"global\": \"5.573896172710042229425653204089\", \"active\": \"8.910740528931908599474809307139\", \"challenge\": \"16.482492055414464715568101382814\", \"sincerity\": \"7.239090558243146844574766873848\", \"communication\": \"1.964643070690589521376523407525\", \"patient\": \"23.281534278167008267246274044737\", \"honesty\": \"3.819763533802571053854535421124\", \"responsibility\": \"20.154887524604880866263556526974\", \"creative\": \"8.114174523162326124747778521851\", \"teamwork\": \"4.458777754273067550627729360713\"}, \"user\": {\"global\": \"5.573896172710042229425653204089\", \"active\": \"8.910740528931908599474809307139\", \"challenge\": \"16.482492055414464715568101382814\", \"sincerity\": \"7.239090558243146844574766873848\", \"communication\": \"1.964643070690589521376523407525\", \"patient\": \"23.281534278167008267246274044737\", \"honesty\": \"3.819763533802571053854535421124\", \"responsibility\": \"20.154887524604880866263556526974\", \"creative\": \"8.114174523162326124747778521851\", \"teamwork\": \"4.458777754273067550627729360713\"}, \"Holland\": {\"S\": \"3.999105665891233396536108557484\", \"E\": \"13.415573658028394632424351584632\", \"C\": \"11.446796123404242351284665346611\"}, \"company\": {\"CJ\": \"81.245907660976442343780945520848\", \"samsung\": \"10.951223335081108345434586226474\", \"SK\": \"7.802869003942451087141307652928\"}, \"choice_company\": {\"global\": \"50.891718891241453093243762850761\", \"active\": \"67.204184028619422974770714063197\", \"challenge\": \"133.688593191513973579276353120804\", \"sincerity\": \"33.971192368037705477945564780384\", \"communication\": \"20.658031800333233718447445426136\", \"patient\": \"385.521799972859753324883058667183\", \"honesty\": \"164.692328949856289455055957660079\", \"responsibility\": \"131.434918155824334462522529065609\", \"creative\": \"233.560436943847264501528115943074\", \"teamwork\": \"81.474831067396650041700922884047\"}, \"first_company\": {\"global\": \"60.008720188573882126092939870432\", \"active\": \"67.286922472137391082469548564404\", \"challenge\": \"119.915655986167422497601364739239\", \"sincerity\": \"34.125561725897178178001922788098\", \"communication\": \"28.532671642718675286687357584015\", \"patient\": \"298.918626284244737689732573926449\", \"honesty\": \"215.064787143203773212007945403457\", \"responsibility\": \"116.558349733934818459601956419647\", \"creative\": \"221.080216093833797685874742455781\", \"teamwork\": \"87.456853010106712531523953657597\"}}";
//        String testString2 = "{\"q1\": {\"global\": \"5.573896172710042229425653204089\", \"active\": \"8.910740528931908599474809307139\", \"challenge\": \"16.482492055414464715568101382814\", \"sincerity\": \"7.239090558243146844574766873848\", \"communication\": \"1.964643070690589521376523407525\", \"patient\": \"23.281534278167008267246274044737\", \"honesty\": \"3.819763533802571053854535421124\", \"responsibility\": \"20.154887524604880866263556526974\", \"creative\": \"8.114174523162326124747778521851\", \"teamwork\": \"4.458777754273067550627729360713\"}, \"user\": {\"global\": \"5.573896172710042229425653204089\", \"active\": \"8.910740528931908599474809307139\", \"challenge\": \"16.482492055414464715568101382814\", \"sincerity\": \"7.239090558243146844574766873848\", \"communication\": \"1.964643070690589521376523407525\", \"patient\": \"23.281534278167008267246274044737\", \"honesty\": \"3.819763533802571053854535421124\", \"responsibility\": \"20.154887524604880866263556526974\", \"creative\": \"8.114174523162326124747778521851\", \"teamwork\": \"4.458777754273067550627729360713\"}, \"Holland\": {\"S\": \"3.999105665891233396536108557484\", \"E\": \"13.415573658028394632424351584632\", \"C\": \"11.446796123404242351284665346611\"}, \"company\": {\"CJ\": \"81.245907660976442343780945520848\", \"samsung\": \"10.951223335081108345434586226474\", \"SK\": \"7.802869003942451087141307652928\"}, \"choice_company\": {\"global\": \"50.891718891241453093243762850761\", \"active\": \"67.204184028619422974770714063197\", \"challenge\": \"133.688593191513973579276353120804\", \"sincerity\": \"33.971192368037705477945564780384\", \"communication\": \"20.658031800333233718447445426136\", \"patient\": \"385.521799972859753324883058667183\", \"honesty\": \"164.692328949856289455055957660079\", \"responsibility\": \"131.434918155824334462522529065609\", \"creative\": \"233.560436943847264501528115943074\", \"teamwork\": \"81.474831067396650041700922884047\"}, \"first_company\": {\"global\": \"60.008720188573882126092939870432\", \"active\": \"67.286922472137391082469548564404\", \"challenge\": \"119.915655986167422497601364739239\", \"sincerity\": \"34.125561725897178178001922788098\", \"communication\": \"28.532671642718675286687357584015\", \"patient\": \"298.918626284244737689732573926449\", \"honesty\": \"215.064787143203773212007945403457\", \"responsibility\": \"116.558349733934818459601956419647\", \"creative\": \"221.080216093833797685874742455781\", \"teamwork\": \"87.456853010106712531523953657597\"}}";
//
//        JSONObject t1 = (JSONObject) new JSONParser().parse(testString1);
//        JSONObject t2 = (JSONObject) new JSONParser().parse(testString2);

        model.addAttribute("username", principal.getName());
        model.addAttribute("company", userSubmitVO.getCompany());
        model.addAttribute("job", userSubmitVO.getJob());

        userSubmitVO.companyToEng();
        userSubmitVO.jobToEng();

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("usertest");
        Table tableCompany = dynamoDB.getTable("Company");
        Table tableJob = dynamoDB.getTable("Job");

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#data", "Data");

        GetItemSpec spec1 = new GetItemSpec()
                .withPrimaryKey("timestamp", "2019.05.30.09.11.04")
                .withProjectionExpression("#data")
                .withNameMap(nameMap);
        //                .withPrimaryKey("timestamp", userSubmitVO.getTimestamp(0))

        GetItemSpec spec2 = new GetItemSpec()
                .withPrimaryKey("timestamp", "2019.05.30.15.08.16")
                .withProjectionExpression("#data")
                .withNameMap(nameMap);
//        .withPrimaryKey("UID", userSubmitVO.getTimestamp(1))

        GetItemSpec specCompany = new GetItemSpec()
                .withPrimaryKey("Company_Name", userSubmitVO.getCompany())
                .withProjectionExpression("#data, talent")
                .withNameMap(nameMap);

        GetItemSpec specJob = new GetItemSpec()
                .withPrimaryKey("Job_Name", userSubmitVO.getJob())
                .withProjectionExpression("#data, talent")
                .withNameMap(nameMap);


        Item item = tableCompany.getItem(specCompany);
        System.out.println("item is : " + item);
        Map itemCompany = (Map) item.get("Data");
        System.out.println("itemCompany is : " + itemCompany);
        String talentCompany = (String) item.get("talent");
//        System.out.println(talentCompany);

        item = tableJob.getItem(specJob);
        Map itemJob = (Map) item.get("Data");
        String talentJob = (String) item.get("talent");
//        System.out.println(talentJob);

//        선택 기업 DB 데이터
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


//        선택 직무 DB 데이터
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



//        1번 데이터

        item = table.getItem(spec1);
        Map item1 = (Map) item.get("Data");

        Map item1_choice_company = (Map) item1.get("choice_company");
        Map item1_job = (Map) item1.get("user");
        Map item1_company = (Map) item1.get("company");

//        1번 기업 데이터

        model.addAttribute("active_company_1",            item1_choice_company.get("active"));
        model.addAttribute("challenge_company_1",         item1_choice_company.get("challenge"));
        model.addAttribute("communication_company_1",     item1_choice_company.get("communication"));
        model.addAttribute("creative_company_1",          item1_choice_company.get("creative"));
        model.addAttribute("global_company_1",            item1_choice_company.get("global"));
        model.addAttribute("honesty_company_1",           item1_choice_company.get("honesty"));
        model.addAttribute("patient_company_1",           item1_choice_company.get("patient"));
        model.addAttribute("responsibility_company_1",    item1_choice_company.get("responsibility"));
        model.addAttribute("sincerity_company_1",         item1_choice_company.get("sincerity"));
        model.addAttribute("teamwork_company_1",          item1_choice_company.get("teamwork"));

//        1번 직무 데이터

        model.addAttribute("active_Job_1",            item1_job.get("active"));
        model.addAttribute("challenge_Job_1",         item1_job.get("challenge"));
        model.addAttribute("communication_Job_1",     item1_job.get("communication"));
        model.addAttribute("creative_Job_1",          item1_job.get("creative"));
        model.addAttribute("global_Job_1",            item1_job.get("global"));
        model.addAttribute("honesty_Job_1",           item1_job.get("honesty"));
        model.addAttribute("patient_Job_1",           item1_job.get("patient"));
        model.addAttribute("responsibility_Job_1",    item1_job.get("responsibility"));
        model.addAttribute("sincerity_Job_1",         item1_job.get("sincerity"));
        model.addAttribute("teamwork_Job_1",          item1_job.get("teamwork"));

//        1번 추천 기업 데이터

        ArrayList<Float> stringList = new ArrayList<Float>();


        if(item1_company.containsKey("SK")) stringList.add(Float.parseFloat((String) item1_company.get("SK")));
        if(item1_company.containsKey("LG")) stringList.add(Float.parseFloat((String) item1_company.get("LG")));
        if(item1_company.containsKey("CJ")) stringList.add(Float.parseFloat((String) item1_company.get("CJ")));
        if(item1_company.containsKey("samsung")) stringList.add(Float.parseFloat((String) item1_company.get("samsung")));
        if(item1_company.containsKey("hyundai")) stringList.add(Float.parseFloat((String) item1_company.get("hyundai")));

        Collections.sort(stringList, new AscendingString());

        ArrayList<String> n = new ArrayList<String>();

        for(int i=0;i<3;i++){
            if(item1_company.containsKey("SK"))
            {
                if(stringList.get(i) == Float.parseFloat(getUC( item1_company, "SK"))) n.add("SK");
            }
            if(item1_company.containsKey("LG"))
            {
                if(stringList.get(i) == Float.parseFloat(getUC( item1_company, "LG"))) n.add("LG");
            }
            if(item1_company.containsKey("CJ"))
            {
                if(stringList.get(i) == Float.parseFloat(getUC( item1_company, "CJ"))) n.add("CJ");
            }
            if(item1_company.containsKey("hyundai"))
            {
                if(stringList.get(i) == Float.parseFloat(getUC( item1_company, "hyundai"))) n.add("현대");
            }
            if(item1_company.containsKey("samsung"))
            {
                if(stringList.get(i) == Float.parseFloat(getUC( item1_company, "samsung"))) n.add("삼성");
            }
        }

        model.addAttribute("n1_company_1", n.get(0));
        model.addAttribute("n2_company_1", n.get(1));
        model.addAttribute("n3_company_1", n.get(2));

        model.addAttribute("n1_value_1", stringList.get(0));
        model.addAttribute("n2_value_1", stringList.get(1));
        model.addAttribute("n3_value_1", stringList.get(2));




//        2번 데이터

        item = table.getItem(spec2);
        Map item2 = (Map) item.get("Data");

        Map item2_choice_company = (Map) item2.get("choice_company");
        Map item2_job = (Map) item2.get("user");
        Map item2_company = (Map) item2.get("company");

//        item = table.getItem(spec2);
//        JSONObject item2 = (JSONObject) item.get("Data");
//        JSONObject item2_choice_company = (JSONObject) item2.get("choice_company");
//        JSONObject item2_job = (JSONObject) item2.get("user");
//        JSONObject item2_company = (JSONObject) item2.get("company");

//        System.out.println(item1);
//        System.out.println(item2);

//        JSONObject item2_choice_company = (JSONObject) t2.get("choice_company");
//        JSONObject item2_job = (JSONObject) t2.get("user");
//        JSONObject item2_company = (JSONObject) t2.get("company");

//        2번 기업 데이터



        model.addAttribute("active_company_2",            item2_choice_company.get("active"));
        model.addAttribute("challenge_company_2",         item2_choice_company.get("challenge"));
        model.addAttribute("communication_company_2",     item2_choice_company.get("communication"));
        model.addAttribute("creative_company_2",          item2_choice_company.get("creative"));
        model.addAttribute("global_company_2",            item2_choice_company.get("global"));
        model.addAttribute("honesty_company_2",           item2_choice_company.get("honesty"));
        model.addAttribute("patient_company_2",           item2_choice_company.get("patient"));
        model.addAttribute("responsibility_company_2",    item2_choice_company.get("responsibility"));
        model.addAttribute("sincerity_company_2",         item2_choice_company.get("sincerity"));
        model.addAttribute("teamwork_company_2",          item2_choice_company.get("teamwork"));

//        1번 직무 데이터

        model.addAttribute("active_Job_2",            item2_job.get("active"));
        model.addAttribute("challenge_Job_2",         item2_job.get("challenge"));
        model.addAttribute("communication_Job_2",     item2_job.get("communication"));
        model.addAttribute("creative_Job_2",          item2_job.get("creative"));
        model.addAttribute("global_Job_2",            item2_job.get("global"));
        model.addAttribute("honesty_Job_2",           item2_job.get("honesty"));
        model.addAttribute("patient_Job_2",           item2_job.get("patient"));
        model.addAttribute("responsibility_Job_2",    item2_job.get("responsibility"));
        model.addAttribute("sincerity_Job_2",         item2_job.get("sincerity"));
        model.addAttribute("teamwork_Job_2",          item2_job.get("teamwork"));

//        1번 추천 기업 데이터

        ArrayList<Float> stringList2 = new ArrayList<Float>();


        if(item2_company.containsKey("SK")) stringList2.add(Float.parseFloat((String) item2_company.get("SK")));
        if(item2_company.containsKey("LG")) stringList2.add(Float.parseFloat((String) item2_company.get("LG")));
        if(item2_company.containsKey("CJ")) stringList2.add(Float.parseFloat((String) item2_company.get("CJ")));
        if(item2_company.containsKey("samsung")) stringList2.add(Float.parseFloat((String) item2_company.get("samsung")));
        if(item2_company.containsKey("hyundai")) stringList2.add(Float.parseFloat((String) item2_company.get("hyundai")));

        Collections.sort(stringList2, new AscendingString());

        ArrayList<String> n2 = new ArrayList<String>();

        for(int i=0;i<3;i++){
            if(item2_company.containsKey("SK"))
            {
                if(stringList2.get(i) == Float.parseFloat(getUC(item2_company, "SK"))) n2.add("SK");
            }
            if(item2_company.containsKey("LG"))
            {
                if(stringList2.get(i) == Float.parseFloat(getUC(item2_company, "LG"))) n2.add("LG");
            }
            if(item2_company.containsKey("CJ"))
            {
                if(stringList2.get(i) == Float.parseFloat(getUC(item2_company, "CJ"))) n2.add("CJ");
            }
            if(item2_company.containsKey("hyundai"))
            {
                if(stringList2.get(i) == Float.parseFloat(getUC(item2_company, "hyundai"))) n2.add("현대");
            }
            if(item2_company.containsKey("samsung"))
            {
                if(stringList2.get(i) == Float.parseFloat(getUC(item2_company, "samsung"))) n2.add("삼성");
            }
        }
//
        model.addAttribute("n1_company_2", n2.get(0));
        model.addAttribute("n2_company_2", n2.get(1));
        model.addAttribute("n3_company_2", n2.get(2));

        model.addAttribute("n1_value_2", stringList2.get(0));
        model.addAttribute("n2_value_2", stringList2.get(1));
        model.addAttribute("n3_value_2", stringList2.get(2));

        return "compare_result";
    }

    public String getUC(Map jo,String c) {
        return jo.get(c).toString();
    }

    class AscendingString implements Comparator<Float> {

        @Override
        public int compare(Float a, Float b){
            return b.compareTo(a);
        }
    }

}

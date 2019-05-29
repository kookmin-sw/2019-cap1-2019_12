package com.capstone.server.dbtest;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.List;
import java.util.Map;

public class DbTest {
    public static void main(String[] args) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        SecurityProperties.User user = (SecurityProperties.User) authentication.getPrincipal();

        System.out.println(authentication.getPrincipal());



//        String testresult = "{\n" +
//                "  \"user\": {\n" +
//                "    \"global\": \"0.427754194822695110733690171401\",\n" +
//                "    \"active\": \"47.163446436762249902585608651862\",\n" +
//                "    \"challenge\": \"12.902232102841789895819601952098\",\n" +
//                "    \"sincerity\": \"7.537250181366866641496926604304\",\n" +
//                "    \"communication\": \"27.252393582106538616471880231984\",\n" +
//                "    \"patient\": \"1.242384842161574631802523072110\",\n" +
//                "    \"honesty\": \"0.103709286657178670343348869665\",\n" +
//                "    \"responsibility\": \"0.115360158672077398045452412134\",\n" +
//                "    \"creative\": \"3.242753112501177259474616221269\",\n" +
//                "    \"teamwork\": \"0.012716102107846224966714032689\"\n" +
//                "  },\n" +
//                "  \"Holland\": {\n" +
//                "    \"S\": \"9.230954626345694080669090908486\",\n" +
//                "    \"E\": \"15.855947952694323888067629013676\",\n" +
//                "    \"C\": \"8.883344310185620429365371819586\"\n" +
//                "  },\n" +
//                "  \"company\": {\n" +
//                "    \"CJ\": \"-0.710577782455781070680700395314\",\n" +
//                "    \"hyundai\": \"-0.915892799011034486333926452062\",\n" +
//                "    \"SK\": \"-0.948065695100847150023071208125\",\n" +
//                "    \"samsung\": \"-0.973075566048450801837077506207\",\n" +
//                "    \"LG\": \"-1.003086331581821921687946996826\"\n" +
//                "  },\n" +
//                "  \"choice_company\": {\n" +
//                "    \"global\": \"15.120075545649813264503791288007\",\n" +
//                "    \"active\": \"221.651558648623222325113601982594\",\n" +
//                "    \"challenge\": \"101.487942076313657935315859504044\",\n" +
//                "    \"sincerity\": \"25.043280813300039966406984603964\",\n" +
//                "    \"communication\": \"495.057913638720151539018843322992\",\n" +
//                "    \"patient\": \"24.861037275833705706418186309747\",\n" +
//                "    \"honesty\": \"4.472471820222674310230104310904\",\n" +
//                "    \"responsibility\": \"1.126530495645172713636839034734\",\n" +
//                "    \"creative\": \"67.353443958855763185056275688112\",\n" +
//                "    \"teamwork\": \"0.244215665280616722743189939138\"\n" +
//                "  },\n" +
//                "  \"first_company\": {\n" +
//                "    \"global\": \"11.459025573741156378559935546946\",\n" +
//                "    \"active\": \"219.529810615670271545241121202707\",\n" +
//                "    \"challenge\": \"104.883226251792692096387327183038\",\n" +
//                "    \"sincerity\": \"25.068460139101777173209484317340\",\n" +
//                "    \"communication\": \"462.384366674848138245579320937395\",\n" +
//                "    \"patient\": \"29.154970559822036335617667646147\",\n" +
//                "    \"honesty\": \"4.496185592909177408671439479804\",\n" +
//                "    \"responsibility\": \"1.047610343540322341837622843741\",\n" +
//                "    \"creative\": \"69.992633540079012277601577807218\",\n" +
//                "    \"teamwork\": \"0.295138039238148575105213922143\"\n" +
//                "  }\n" +
//                "}";
//
//        JSONObject feedback = (JSONObject) new JSONParser().parse(testresult);
//        JSONObject feedback_user = (JSONObject) feedback.get("user");
//
//
//        System.out.println(feedback);
//        System.out.println(feedback_user.get("global"));

//        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
//                .withRegion(Regions.AP_NORTHEAST_2)
//                .build();
//
//        DynamoDB dynamoDB = new DynamoDB(client);
//
//        DynamoDBMapper mapper = new DynamoDBMapper(client);
//
//        Table table = dynamoDB.getTable("Company");
//
//        GetItemSpec spec = new GetItemSpec().withPrimaryKey("Company_Name", "lg");
//
//
//        try {
//            System.out.println("Attempting to read the company data...");
//            Item outcome = table.getItem(spec);
//            Map out2 = (Map) outcome.get("Data");
//            System.out.println("GetItem succeeded: " + out2.get("active"));
//        }
//        catch (Exception e) {
//            System.err.println("Unable to read item: " + "lg" );
//            System.err.println(e.getMessage());
//        }


    }
}
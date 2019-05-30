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


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbTest {
    public static void main(String[] args) throws Exception {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("usertest");
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#data", "Data");
        nameMap.put("#timestamp", "timestamp");
        nameMap.put("#email", "email");

//        Get 쿼리문 설정
        GetItemSpec spec = new GetItemSpec()
//                .with
                .withPrimaryKey("timestamp", "kyungkoh")
                .withProjectionExpression("#email, #timestamp, #data")
                .withNameMap(nameMap);

//        Item item = table.getItem("email", "kyungkoh");
        Map item = (Map) table.getItem(spec);
//        table.get

        System.out.println(item);


    }
}
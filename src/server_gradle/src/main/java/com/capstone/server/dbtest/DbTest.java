package com.capstone.server.dbtest;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DbTest {
    public static void main(String[] args) throws Exception {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("compare");

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#ts", "timestamp");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":times", "2019.05.30.20.22.10");

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("numstamp = :v_num")
                .withValueMap(new ValueMap().withString(":v_num", "20190530140014"));
//                .withValueMap(new ValueMap()
//                        .withString(":v_timestamp", "2019.05.30.20.22.10"))
//                .withMaxPageSize(10)
//                .withScanIndexForward(false);

        ItemCollection<QueryOutcome> items = table.query(spec);
        System.out.println(items);

//        Iterator<Item> iterator = items.iterator();
//        Item item = null;
//        while (iterator.hasNext()) {
//            item = iterator.next();
//            System.out.println(item.toJSONPretty());
//        }


    }
}
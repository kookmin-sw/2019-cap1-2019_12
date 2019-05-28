//package com.capstone.server.test;
//
//
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
//import com.amazonaws.services.dynamodbv2.document.*;
//import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
//import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
//
//import java.util.Iterator;
//
//public class DynamoQuery {
//
//    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
//            .withRegion(Regions.AP_NORTHEAST_2).build();
//    DynamoDB dynamoDB = new DynamoDB(client);
//
//    Table table = dynamoDB.getTable("Reply");
//
//    QuerySpec spec = new QuerySpec()
//            .withKeyConditionExpression("Id = :v_id")
//            .withValueMap(new ValueMap()
//                    .withString(":v_id", "Amazon DynamoDB#DynamoDB Thread 1"));
//
//    ItemCollection<QueryOutcome> items = table.query(spec);
//
//    Iterator<Item> iterator = items.iterator();
//    Item item = null;
//    while (iterator.hasNext()) {
//            item = iterator.next();
//            System.out.println(item.toJSONPretty());
//    }
//}

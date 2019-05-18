//package com.capstone.server.dbtest;
//
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
//import com.amazonaws.services.dynamodbv2.document.DynamoDB;
//import com.amazonaws.services.dynamodbv2.document.Item;
//import com.amazonaws.services.dynamodbv2.document.Table;
//import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
//
//public class DbTest {
//    public static void main(String[] args) throws Exception {
//
//        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
//                .withRegion(Regions.AP_NORTHEAST_2)
//                .build();
//
//        DynamoDB dynamoDB = new DynamoDB(client);
//
//        Table table = dynamoDB.getTable("test");
//
//        String id = "201905111226";
//
//
//        GetItemSpec spec = new GetItemSpec().withPrimaryKey("UID", id);
//
//        try {
//            System.out.println("Attempting to read the item...");
//            Item outcome = table.getItem(spec);
//            System.out.println("GetItem succeeded: " + outcome);
//        }
//        catch (Exception e) {
//            System.err.println("Unable to read item: " + id );
//            System.err.println(e.getMessage());
//        }
//
//    }
//}
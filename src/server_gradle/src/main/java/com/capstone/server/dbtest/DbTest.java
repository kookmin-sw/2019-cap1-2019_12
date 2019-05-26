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

import java.util.List;
import java.util.Map;

public class DbTest {



    public static void main(String[] args) throws Exception {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        DynamoDBMapper mapper = new DynamoDBMapper(client);

        Table table = dynamoDB.getTable("Company");

        GetItemSpec spec = new GetItemSpec().withPrimaryKey("Company_Name", "lg");


        try {
            System.out.println("Attempting to read the company data...");
            Item outcome = table.getItem(spec);
            Map out2 = (Map) outcome.get("Data");
            System.out.println("GetItem succeeded: " + out2.get("active"));
        }
        catch (Exception e) {
            System.err.println("Unable to read item: " + "lg" );
            System.err.println(e.getMessage());
        }


    }
}
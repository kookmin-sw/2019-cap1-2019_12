package com.capstone.server.dbtest;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Map;

@DynamoDBTable(tableName = "Company")
public class CompanyItem {

    private String Company_Name;
    private Map<String, Number> Data;
    private Map<String, String> Main;

    @DynamoDBHashKey(attributeName = "Company_Name")
    public String getCompany_Name() {
        return Company_Name;
    }
    public void setCompany_Name(String company_Name) {
        Company_Name = company_Name;
    }

    @DynamoDBAttribute(attributeName = "Data")
    public Map<String, Number> getData() {
        return Data;
    }
    public void setData(Map<String, Number> data) {
        Data = data;
    }

    @DynamoDBAttribute(attributeName = "Main")
    public Map<String, String> getMain() {
        return Main;
    }
    public void setMain(Map<String, String> main) {
        Main = main;
    }
}

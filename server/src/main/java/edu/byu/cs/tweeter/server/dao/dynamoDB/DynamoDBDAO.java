package edu.byu.cs.tweeter.server.dao.dynamoDB;

import com.amazonaws.services.dynamodbv2.document.Table;

public class DynamoDBDAO {
    private String region;
    private DynamoDBFacade dynamoDB;
    private Table table;

    public DynamoDBDAO(String tableName) {
        region = "us-west-2";
        dynamoDB = new DynamoDBFacade(region);
        table = dynamoDB.getTableByName(tableName);
    }

    public String getRegion() {
        return region;
    }

    protected DynamoDBFacade getDynamoDB() {
        return dynamoDB;
    }

    protected Table getTable() {
        return table;
    }
}

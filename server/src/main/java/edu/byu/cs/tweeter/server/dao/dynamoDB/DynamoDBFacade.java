package edu.byu.cs.tweeter.server.dao.dynamoDB;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;

public class DynamoDBFacade {
    private final DynamoDB dynamoDB;

    public DynamoDBFacade(String region) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(region)
                .build();

        dynamoDB = new DynamoDB(client);
    }

    public Table getTableByName(String name) {
        return dynamoDB.getTable(name);
    }
}

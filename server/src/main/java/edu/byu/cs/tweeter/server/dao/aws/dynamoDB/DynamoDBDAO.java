package edu.byu.cs.tweeter.server.dao.aws.dynamoDB;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;

public class DynamoDBDAO {
    private final String region;
    private final DynamoDBFacade dynamoDB;
    private final Table table;

    public DynamoDBDAO(String tableName, String region) {
        this.region = region;
        this.dynamoDB = new DynamoDBFacade(region);
        this.table = dynamoDB.getTableByName(tableName);
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

    public TableWriteItems makeBatch() {
        return new TableWriteItems(getTable().getTableName());
    }
}

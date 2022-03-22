package edu.byu.cs.tweeter.server.dao.dynamoDB;

import com.amazonaws.services.dynamodbv2.document.Table;

public class DynamoDBDAO {
    private DynamoDBFacade facade;
    private Table table;

    public DynamoDBDAO(String tableName) {
        facade = new DynamoDBFacade("us-west-2");

        table = facade.getTableByName(tableName);
    }

    protected Table getTable() {
        return table;
    }
}

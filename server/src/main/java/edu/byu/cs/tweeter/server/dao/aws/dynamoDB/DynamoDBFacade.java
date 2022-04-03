package edu.byu.cs.tweeter.server.dao.aws.dynamoDB;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.util.List;
import java.util.Map;

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

    public PutItemOutcome putItemInTable(String itemType, Item item, Table table) {
        PutItemOutcome outcome;

        try {
            System.out.println("Insert " + itemType);

            outcome = table.putItem(item);

            if (outcome == null) {
                System.out.println("Put " + itemType + " failed");

                throw new RuntimeException("[Server Error] Insert " + itemType + " failed");

            } else {
                System.out.println("Insert " + itemType + " succeeded:\n" + outcome.getPutItemResult());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            throw new RuntimeException("[Server Error] Insert " + itemType + " failed");
        }

        return outcome;
    }

    public Item getItemFromTable(String itemType, GetItemSpec spec, Table table) {
        Item outcome = null;

        try {
            System.out.println("Get " + itemType);

            outcome = table.getItem(spec);

            if (outcome == null) {
                System.out.println("Get " + itemType + " failed");

                throw new RuntimeException("[Bad Request] " + itemType + " not found");

            } else {
                System.out.println("Get " + itemType + " succeeded: " + outcome);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return outcome;
    }

    public void deleteItemFromTable(String itemType, DeleteItemSpec spec, Table table) {
        DeleteItemOutcome outcome;

        try {
            System.out.println("Delete " + itemType);

            outcome = table.deleteItem(spec);

            if (outcome == null) {
                System.out.println("Delete " + itemType + " failed");

                throw new RuntimeException("[Bad Request] " + itemType + " not deleted");

            } else {
                System.out.println("Delete " + itemType + " succeeded");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void writeBatch(String itemType, TableWriteItems items) {
        try {
            System.out.println("Write batch of type " + itemType);
            // The 'dynamoDB' object is of type DynamoDB and is declared statically in this example
            BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(items);

            System.out.println("Write batch succeeded");

            // Check the outcome for items that didn't make it onto the table
            // If any were not added to the table, try again to write the batch
            while (outcome.getUnprocessedItems().size() > 0) {
                Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();

                System.out.println("Found " + outcome.getUnprocessedItems().size() + " unprocessed items of type " + itemType);
                System.out.println("Write batch of unprocessed items of type " + itemType);

                outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);

                System.out.println("Write batch succeeded");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}

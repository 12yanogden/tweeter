package edu.byu.cs.tweeter.server.dao.aws.dynamoDB;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;

public class DynamoDBAuthTokenDAO extends DynamoDBDAO implements AuthTokenDAO {
    private final String itemType;
    private final String tokenAttr;
    private final String dateTimeAttr;

    public DynamoDBAuthTokenDAO(String region) {
        super("authToken", region);

        this.itemType = "AuthToken";
        this.tokenAttr = "token";
        this.dateTimeAttr = "dateTime";
    }

    @Override
    public AuthToken getAuthToken(String token) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(getTokenAttr(), token);
        Item outcome = getDynamoDB().getItemFromTable(getItemType(), spec, getTable());

        return itemToAuthToken(outcome);
    }

    @Override
    public void putAuthToken(AuthToken authToken) {
        Item item = new Item()
                .withPrimaryKey(
                        getTokenAttr(),
                        authToken.getToken())
                .withString(
                        getDateTimeAttr(),
                        authToken.getDatetime());

        getDynamoDB().putItemInTable(getItemType(), item, getTable());

        System.out.println("exit putAuthToken");
    }

    @Override
    public void deleteAuthToken(String token) {
        DeleteItemSpec spec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey(getTokenAttr(), token));

        getDynamoDB().deleteItemFromTable(getItemType(), spec, getTable());
    }

    private AuthToken itemToAuthToken(Item item) {
        return new AuthToken(item.get(getTokenAttr()).toString(),
                item.get(getDateTimeAttr()).toString());
    }

    public String getItemType() {
        return itemType;
    }

    public String getTokenAttr() {
        return tokenAttr;
    }

    public String getDateTimeAttr() {
        return dateTimeAttr;
    }
}

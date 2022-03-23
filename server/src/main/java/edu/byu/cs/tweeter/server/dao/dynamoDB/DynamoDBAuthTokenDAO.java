package edu.byu.cs.tweeter.server.dao.dynamoDB;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class DynamoDBAuthTokenDAO extends DynamoDBDAO {
    Table authTokenTable;

    public DynamoDBAuthTokenDAO() {
        this.authTokenTable = getDynamoDB().getTableByName("authToken");
    }

    public void validateAuthToken(AuthToken authToken) {

        // TODO: Determine how long an authToken is supposed to last and how the dateTime is implemented
    }

    public AuthToken getAuthToken(String token) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("token", token);
        Item outcome;

        try {
            System.out.println("getAuthToken: " + token);

            outcome = getAuthTokenTable().getItem(spec);

            System.out.println("getAuthToken succeeded: " + outcome);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            throw new RuntimeException("[Bad Request] AuthToken \"" + token + "\" not found");
        }

        return new AuthToken(outcome.get("token").toString(),
                outcome.get("dateTime").toString());
    }

    public void putAuthToken(AuthToken authToken) {
        System.out.println("Put AuthToken: " + authToken.getToken());

        PutItemOutcome outcome = getAuthTokenTable().putItem(
                new Item()
                        .withPrimaryKey(
                                "token",
                                authToken.getToken())
                        .withString(
                                "dateTime",
                                authToken.getDatetime())
        );

        System.out.println("Put AuthToken succeeded:\n" + outcome.getPutItemResult());
    }

    public Table getAuthTokenTable() {
        return authTokenTable;
    }
}

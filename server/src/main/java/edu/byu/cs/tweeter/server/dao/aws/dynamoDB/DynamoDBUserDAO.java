package edu.byu.cs.tweeter.server.dao.aws.dynamoDB;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.util.Pair;

public class DynamoDBUserDAO extends DynamoDBDAO implements UserDAO  {
    private final String itemType;
    private final String aliasAttr;
    private final String firstNameAttr;
    private final String lastNameAttr;
    private final String passwordAttr;
    private final String imageURLAttr;
    private final String followingCountAttr;
    private final String followerCountAttr;

    public DynamoDBUserDAO(String region) {
        super("user", region);

        itemType = "user";
        aliasAttr = "alias";
        firstNameAttr = "firstName";
        lastNameAttr = "lastName";
        passwordAttr = "password";
        imageURLAttr = "imageURL";
        followingCountAttr = "followingCount";
        followerCountAttr = "followerCount";
    }

    @Override
    public void putUser(User user, String password) {
        getDynamoDB().putItemInTable(getItemType(), makeItem(user, password), getTable());
    }

    @Override
    public void putUsers(List<Pair<User, String>> users) {
        int MAX_BATCH_SIZE = 25;
        TableWriteItems items = makeBatch();

        for (Pair<User, String> pair: users) {
            User user = pair.getFirst();
            String password = pair.getSecond();

            items.addItemToPut(makeItem(user, password));

            if (items.getItemsToPut() != null && items.getItemsToPut().size() == MAX_BATCH_SIZE) {
                getDynamoDB().writeBatch("user", items);
                items = makeBatch();
            }
        }
    }

    private Item makeItem(User user, String password) {
        return new Item()
                .withPrimaryKey(
                        getAliasAttr(), user.getAlias()
                )
                .withString(
                        getFirstNameAttr(), user.getFirstName()
                )
                .withString(
                        getLastNameAttr(), user.getLastName()
                )
                .withString(
                        getPasswordAttr(), password
                )
                .withString(
                        getImageURLAttr(), user.getImageUrl()
                )
                .withInt(
                        getFollowingCountAttr(), 0
                )
                .withInt(
                        getFollowerCountAttr(), 0
                );
    }

    @Override
    public User getUser(String alias) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(getAliasAttr(), alias);
        Item dbResponse = getDynamoDB().getItemFromTable(getItemType(), spec, getTable());

        return extractUserFromItem(dbResponse);
    }

    @Override
    public User getUser(String alias, String password) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(getAliasAttr(), alias);
        Item dbResponse = getDynamoDB().getItemFromTable(getItemType(), spec, getTable());
        String expectedPassword = dbResponse.get(getPasswordAttr()).toString();
        User user = null;

        System.out.println("expectedPassword.equals(password): " + expectedPassword.equals(password));
        if (expectedPassword.equals(password)) {
            user = extractUserFromItem(dbResponse);
        }

        return user;
    }

    @Override
    public int getFollowingCount(String alias) {
        return getCount(alias, "followingCount");
    }

    @Override
    public int getFollowerCount(String alias) {
        return getCount(alias, "followerCount");
    }

    public int getCount(String alias, String countType) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("alias", alias);
        Item outcome = getDynamoDB().getItemFromTable("user", spec, getTable());

        return Integer.parseInt(outcome.get(countType).toString());
    }

    @Override
    public void incrementFollowingCount(String alias) {
        String countType = "followingCount";
        int count = getCount(alias, countType);

        setCount(alias, countType, count + 1);
    }

    @Override
    public void incrementFollowerCount(String alias) {
        String countType = "followerCount";
        int count = getCount(alias, countType);

        setCount(alias, countType, count + 1);
    }

    @Override
    public void decrementFollowingCount(String alias) {
        String countType = "followingCount";
        int count = getCount(alias, countType);

        setCount(alias, countType, count - 1);
    }

    @Override
    public void decrementFollowerCount(String alias) {
        String countType = "followerCount";
        int count = getCount(alias, countType);

        setCount(alias, countType, count - 1);
    }

    private void setCount(String alias, String countType, int count) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("alias", alias)
                .withUpdateExpression("set " + countType + " = :r")
                .withValueMap(new ValueMap().withInt(":r", count))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        System.out.println("Updating " + countType + " for " + alias);

        UpdateItemOutcome outcome = getTable().updateItem(updateItemSpec);

        System.out.println("Update succeeded:\n" + outcome.getItem().toJSONPretty());
    }

    private User extractUserFromItem(Item item) {
        return new User(item.get(getFirstNameAttr()).toString(),
                item.get(getLastNameAttr()).toString(),
                item.get(getAliasAttr()).toString(),
                item.get(getImageURLAttr()).toString());
    }

    public String getItemType() {
        return itemType;
    }

    public String getAliasAttr() {
        return aliasAttr;
    }

    public String getFirstNameAttr() {
        return firstNameAttr;
    }

    public String getLastNameAttr() {
        return lastNameAttr;
    }

    public String getPasswordAttr() {
        return passwordAttr;
    }

    public String getImageURLAttr() {
        return imageURLAttr;
    }

    public String getFollowingCountAttr() {
        return followingCountAttr;
    }

    public String getFollowerCountAttr() {
        return followerCountAttr;
    }
}

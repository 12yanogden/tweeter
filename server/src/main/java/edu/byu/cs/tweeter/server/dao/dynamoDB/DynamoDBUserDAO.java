package edu.byu.cs.tweeter.server.dao.dynamoDB;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.util.Pair;

public class DynamoDBUserDAO extends DynamoDBDAO implements UserDAO  {
    private S3Facade s3;
    private String s3BucketName;
    private String itemType;
    private String aliasAttr;
    private String firstNameAttr;
    private String lastNameAttr;
    private String passwordAttr;
    private String imageURLAttr;
    private String followingCountAttr;
    private String followerCountAttr;

    public DynamoDBUserDAO() {
        super("user");

        s3 = new S3Facade(getRegion());
        s3BucketName = "ogden9-tweeter";
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
    public String putUser(User user, String password, String image) {
        String imageURL;
        Item item;

        // TODO: Validate user alias is unique

        imageURL = getS3().putStreamInBucket(getS3BucketName(),
                                    user.getAlias() + ".png",
                                                toStream(image),
                                                makeMetadata("image/png"));

        item = new Item()
                .withPrimaryKey(
                        getAliasAttr(),
                        user.getAlias(),
                        getFirstNameAttr(),
                        user.getFirstName())
                .withString(
                        getLastNameAttr(),
                        user.getLastName())
                .withString(
                        getPasswordAttr(),
                        hash(password))
                .withString(
                        getImageURLAttr(),
                        imageURL)
                .withInt(
                        getFollowingCountAttr(),
                        0)
                .withInt(
                        getFollowerCountAttr(),
                        0);

        getDynamoDB().putItemInTable(getItemType(), item, getTable());

        return imageURL;
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

        if (expectedPassword.equals(hash(password))) {
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

    private ByteArrayInputStream toStream(String image) {
        byte[] imageBytes = Base64.getDecoder().decode(image);

        return new ByteArrayInputStream(imageBytes);
    }

    private ObjectMetadata makeMetadata(String contentType) {
        ObjectMetadata metadata = new ObjectMetadata();

        metadata.setContentType(contentType);

        return metadata;
    }

    private String hash(String password) {
        byte[] bytes;
        StringBuilder stringBuilder = new StringBuilder();
        String failedMsg = "FAILED TO HASH";
        String out = failedMsg;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(password.getBytes());

            bytes = md.digest();

            for (byte aByte : bytes) {
                stringBuilder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            out = stringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (out.equals(failedMsg)) {
            throw new RuntimeException("[Server Error] Failed to hash: " + password);
        }

        return out;
    }

    public S3Facade getS3() {
        return s3;
    }

    public String getS3BucketName() {
        return s3BucketName;
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

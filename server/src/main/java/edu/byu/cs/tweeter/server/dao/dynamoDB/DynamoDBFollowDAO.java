package edu.byu.cs.tweeter.server.dao.dynamoDB;

import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.util.Pair;

/**
 * A DAO for accessing 'following' data from the database.
 */
public class DynamoDBFollowDAO extends DynamoDBDAO implements FollowDAO {
    private String followeeAliasAttr;
    private String followerAliasAttr;
    private String followeeFirstNameAttr;
    private String followeeLastNameAttr;
    private String followeeImageURLAttr;
    private String followerFirstNameAttr;
    private String followerLastNameAttr;
    private String followerImageURLAttr;

    public DynamoDBFollowDAO() {
        super("follow");

        this.followeeAliasAttr = "followeeAlias";
        this.followerAliasAttr = "followerAlias";
        this.followeeFirstNameAttr = "followeeFirstName";
        this.followeeLastNameAttr = "followeeLastName";
        this.followeeImageURLAttr = "followeeImageURL";
        this.followerFirstNameAttr = "followerFirstName";
        this.followerLastNameAttr = "followerLastName";
        this.followerImageURLAttr = "followerImageURL";
    }

    @Override
    public void putItem(User followee, User follower) {
        Item item = new Item()
                .withPrimaryKey(
                        getFolloweeAliasAttr(),
                        followee.getAlias(),
                        getFollowerAliasAttr(),
                        follower.getAlias())
                .withString(getFolloweeFirstNameAttr(),
                        followee.getFirstName())
                .withString(getFolloweeLastNameAttr(),
                        followee.getLastName())
                .withString(getFolloweeImageURLAttr(),
                        followee.getImageUrl())
                .withString(getFollowerFirstNameAttr(),
                        follower.getFirstName())
                .withString(getFollowerLastNameAttr(),
                        follower.getLastName())
                .withString(getFollowerImageURLAttr(),
                        follower.getImageUrl());

        getDynamoDB().putItemInTable("follow relationship", item, getTable());
    }

    @Override
    public void deleteItem(String followeeAlias, String followerAlias) {
        DeleteItemSpec spec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("followeeAlias", followeeAlias, "followerAlias", followerAlias));

        getDynamoDB().deleteItemFromTable("follow relationship", spec, getTable());
    }

    @Override
    public List<User> getFollowers(String followeeAlias) {
        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#followee", getFolloweeAliasAttr());

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":followee", followeeAlias);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#followee = :followee").withNameMap(nameMap)
                .withValueMap(valueMap);
        querySpec.withScanIndexForward(false);

        List<User> followers = new ArrayList<>();

        try {
            items = getTable().query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                followers.add(extractFollowerFromItem(item));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return followers;
    }

    @Override
    public boolean isFollower(String followeeAlias, String followerAlias) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("followeeAlias", followeeAlias, "followerAlias", followerAlias);
        boolean isFollower = true;

        try {
            getDynamoDB().getItemFromTable("follow relationship", spec, getTable());
        } catch (RuntimeException e) {
            if (e.getMessage().equals("[Server Error] Get follow relationship failed")) {
                isFollower = false;
            }
        }

        return isFollower;
    }

    @Override
    public Pair<List<User>, Boolean> queryFollowing(String followerAlias, int limit, Pair<String, String> lastItemId) {
        ItemCollection<QueryOutcome> items;
        Iterator<Item> iterator;
        Item item;
        boolean hasMoreItems = true;
        QuerySpec querySpec = makeFollowingQuerySpec(followerAlias, limit, lastItemId);
        List<User> following = new ArrayList<>();

        try {
            Index index = getTable().getIndex(getFollowerAliasAttr() + "-" + getFolloweeAliasAttr() + "-index");
            items = index.query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                following.add(extractFolloweeFromItem(item));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (following.size() < limit) {
            hasMoreItems = false;
        }

        return new Pair<>(following, hasMoreItems);
    }

    @Override
    public Pair<List<User>, Boolean> queryFollowers(String followeeAlias, int limit, Pair<String, String> lastItemId) {
        ItemCollection<QueryOutcome> items;
        Iterator<Item> iterator;
        Item item;
        boolean hasMoreItems = true;
        QuerySpec querySpec = makeFollowersQuerySpec(followeeAlias, limit, lastItemId);
        List<User> followers = new ArrayList<>();

        try {
            items = getTable().query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                followers.add(extractFollowerFromItem(item));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (followers.size() < limit) {
            hasMoreItems = false;
        }

        return new Pair<>(followers, hasMoreItems);
    }

    private PrimaryKey pairToKey(Pair<String, String> pair) {
        return new PrimaryKey(getFolloweeAliasAttr(),
                pair.getFirst(),
                getFollowerAliasAttr(),
                pair.getSecond());
    }

    private QuerySpec makeFollowingQuerySpec(String followerAlias, int limit, Pair<String, String> lastItemId) {
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#user", getFollowerAliasAttr());

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":user", followerAlias);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#user = :user").withNameMap(nameMap)
                .withValueMap(valueMap);
        querySpec.withScanIndexForward(true);
        querySpec.withMaxResultSize(limit);

        if (lastItemId.getFirst() != null && lastItemId.getSecond() != null) {
            querySpec.withExclusiveStartKey(pairToKey(lastItemId));
        }

        return querySpec;
    }

    private QuerySpec makeFollowersQuerySpec(String followeeAlias, int limit, Pair<String, String> lastItemId) {
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#user", getFolloweeAliasAttr());

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":user", followeeAlias);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#user = :user").withNameMap(nameMap)
                .withValueMap(valueMap);
        querySpec.withScanIndexForward(true);
        querySpec.withMaxResultSize(limit);

        if (lastItemId.getFirst() != null && lastItemId.getSecond() != null) {
            querySpec.withExclusiveStartKey(pairToKey(lastItemId));
        }

        return querySpec;
    }

    private User extractFolloweeFromItem(Item item) {
        return new User(item.get(getFolloweeFirstNameAttr()).toString(),
                item.get(getFolloweeLastNameAttr()).toString(),
                item.get(getFolloweeAliasAttr()).toString(),
                item.get(getFolloweeImageURLAttr()).toString());
    }

    private User extractFollowerFromItem(Item item) {
        return new User(item.get(getFollowerFirstNameAttr()).toString(),
                item.get(getFollowerLastNameAttr()).toString(),
                item.get(getFollowerAliasAttr()).toString(),
                item.get(getFollowerImageURLAttr()).toString());
    }

    public String getFolloweeAliasAttr() {
        return followeeAliasAttr;
    }

    public String getFollowerAliasAttr() {
        return followerAliasAttr;
    }

    public String getFolloweeFirstNameAttr() {
        return followeeFirstNameAttr;
    }

    public String getFolloweeLastNameAttr() {
        return followeeLastNameAttr;
    }

    public String getFolloweeImageURLAttr() {
        return followeeImageURLAttr;
    }

    public String getFollowerFirstNameAttr() {
        return followerFirstNameAttr;
    }

    public String getFollowerLastNameAttr() {
        return followerLastNameAttr;
    }

    public String getFollowerImageURLAttr() {
        return followerImageURLAttr;
    }
}

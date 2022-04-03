package edu.byu.cs.tweeter.server.dao.aws.dynamoDB;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.util.Pair;

public class DynamoDBStoryDAO extends DynamoDBDAO implements StoryDAO {
    private final String itemType;
    private final String aliasAttr;
    private final String dateTimeAttr;
    private final String postAttr;
    private final String urlsAttr;
    private final String mentionsAttr;
    private final String firstNameAttr;
    private final String lastNameAttr;
    private final String imageURLAttr;

    public DynamoDBStoryDAO(String region) {
        super("story", region);

        itemType = "status";
        aliasAttr = "alias";
        dateTimeAttr = "dateTime";
        postAttr = "post";
        urlsAttr = "urls";
        mentionsAttr = "mentions";
        firstNameAttr = "firstName";
        lastNameAttr = "lastName";
        imageURLAttr = "imageURL";
    }

    @Override
    public void putItem(Status status) {
        Item item = new Item()
                .withPrimaryKey(
                        getAliasAttr(),
                        status.getUser().getAlias(),
                        getDateTimeAttr(),
                        status.getDate())
                .withString(
                        getPostAttr(),
                        status.getPost())
                .withList(getUrlsAttr(),
                        status.getUrls())
                .withList(getMentionsAttr(),
                        status.getMentions())
                .withString(getFirstNameAttr(),
                        status.getUser().getFirstName())
                .withString(getLastNameAttr(),
                        status.getUser().getLastName())
                .withString(getImageURLAttr(),
                        status.getUser().getImageUrl());

        getDynamoDB().putItemInTable(getItemType(), item, getTable());
    }

    @Override
    public Pair<List<Status>, Boolean> queryStory(String alias, int limit, Pair<String, String> lastItemId) {
        ItemCollection<QueryOutcome> items;
        Iterator<Item> iterator;
        Item item;
        boolean hasMoreItems = true;
        QuerySpec querySpec = makeQuerySpec(alias, limit, lastItemId);
        List<Status> story = new ArrayList<>();

        try {
            items = getTable().query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                story.add(extractStatusFromItem(item));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (story.size() < limit) {
            hasMoreItems = false;
        }

        return new Pair<>(story, hasMoreItems);
    }

    private QuerySpec makeQuerySpec(String alias, int limit, Pair<String, String> lastItemId) {
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#user", getAliasAttr());

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":user", alias);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#user = :user").withNameMap(nameMap)
                .withValueMap(valueMap);
        querySpec.withScanIndexForward(false);
        querySpec.withMaxResultSize(limit);

        if (lastItemId.getFirst() != null && lastItemId.getSecond() != null) {
            querySpec.withExclusiveStartKey(pairToKey(lastItemId));
        }

        return querySpec;
    }

    private Status extractStatusFromItem(Item item) {
        return new Status(item.get(getPostAttr()).toString(),
                extractUserFromItem(item),
                item.get(getDateTimeAttr()).toString(),
                (List<String>) item.get(getUrlsAttr()),
                (List<String>) item.get(getMentionsAttr()));
    }

    private User extractUserFromItem(Item item) {
        return new User(item.get(getFirstNameAttr()).toString(),
                item.get(getLastNameAttr()).toString(),
                item.get(getAliasAttr()).toString(),
                item.get(getImageURLAttr()).toString());
    }

    private PrimaryKey pairToKey(Pair<String, String> pair) {
        return new PrimaryKey(getAliasAttr(),
                pair.getFirst(),
                getDateTimeAttr(),
                pair.getSecond());
    }

    public String getItemType() {
        return itemType;
    }

    public String getAliasAttr() {
        return aliasAttr;
    }

    public String getDateTimeAttr() {
        return dateTimeAttr;
    }

    public String getPostAttr() {
        return postAttr;
    }

    public String getUrlsAttr() {
        return urlsAttr;
    }

    public String getMentionsAttr() {
        return mentionsAttr;
    }

    public String getFirstNameAttr() {
        return firstNameAttr;
    }

    public String getLastNameAttr() {
        return lastNameAttr;
    }

    public String getImageURLAttr() {
        return imageURLAttr;
    }
}

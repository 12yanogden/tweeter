package edu.byu.cs.tweeter.server.dao.dynamoDB;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.util.Pair;

public class DynamoDBStoryDAO extends DynamoDBDAO implements StoryDAO {
    private String itemType;
    private String aliasAttr;
    private String dateTimeAttr;
    private String postAttr;
    private String urlsAttr;
    private String mentionsAttr;
    private String firstNameAttr;
    private String lastNameAttr;
    private String imageURLAttr;

    public DynamoDBStoryDAO() {
        super("story");

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
    public Pair<List<Status>, Boolean> queryStory(String alias, int limit, String lastItemId) {
        ItemCollection<QueryOutcome> items = null;
        Map<String, AttributeValue> lastEvaluatedKey = null;
        Iterator<Item> iterator = null;
        Item item = null;
        boolean hasMoreItems = true;

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#user", getAliasAttr());

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":user", alias);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#user = :user").withNameMap(nameMap)
                .withValueMap(valueMap);
        querySpec.withScanIndexForward(true);
        querySpec.withMaxResultSize(limit);

        List<Status> story = new ArrayList<>();

        while(true) {
            try {
                items = getTable().query(querySpec);

                iterator = items.iterator();
                while (iterator.hasNext()) {
                    item = iterator.next();
                    story.add(extractStatusFromItem(item));
                }

                lastEvaluatedKey = items.getLastLowLevelResult().getQueryResult().getLastEvaluatedKey();

                if (lastEvaluatedKey == null) {
                    hasMoreItems = false;
                    break;
                } else {
                    querySpec.withExclusiveStartKey(calcPrimaryKey(lastEvaluatedKey));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return new Pair<>(story, hasMoreItems);
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

    private PrimaryKey calcPrimaryKey(Map<String, AttributeValue> lastEvaluatedKey) {
        return new PrimaryKey(getAliasAttr(),
                lastEvaluatedKey.get(getAliasAttr()).getS(),
                getDateTimeAttr(),
                lastEvaluatedKey.get(getDateTimeAttr()));
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

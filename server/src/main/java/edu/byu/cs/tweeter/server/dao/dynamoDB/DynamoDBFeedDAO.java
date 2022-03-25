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
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.util.Pair;

public class DynamoDBFeedDAO extends DynamoDBDAO implements FeedDAO {
    private String itemType;
    private String ownerAliasAttr;
    private String dateTimeAttr;
    private String postAttr;
    private String urlsAttr;
    private String mentionsAttr;
    private String authorFirstNameAttr;
    private String authorLastNameAttr;
    private String authorAliasAttr;
    private String authorImageURLAttr;

    public DynamoDBFeedDAO() {
        super("feed");

        itemType = "status";
        ownerAliasAttr = "ownerAlias";
        dateTimeAttr = "dateTime";
        postAttr = "post";
        urlsAttr = "urls";
        mentionsAttr = "mentions";
        authorFirstNameAttr = "firstName";
        authorLastNameAttr = "lastName";
        authorAliasAttr = "alias";
        authorImageURLAttr = "imageURL";
    }

    @Override
    public void putItem(String ownerAlias, Status status) {
        Item item = new Item()
                .withPrimaryKey(
                        getOwnerAliasAttr(),
                        ownerAlias,
                        getDateTimeAttr(),
                        status.getDate())
                .withString(
                        getPostAttr(),
                        status.getPost())
                .withList(
                        getUrlsAttr(),
                        status.getUrls())
                .withList(
                        getMentionsAttr(),
                        status.getMentions())
                .withString(
                        getAuthorFirstNameAttr(),
                        status.getUser().getFirstName())
                .withString(
                        getAuthorLastNameAttr(),
                        status.getUser().getLastName())
                .withString(
                        getAuthorAliasAttr(),
                        status.getUser().getAlias())
                .withString(getAuthorImageURLAttr(),
                        status.getUser().getImageUrl());

        getDynamoDB().putItemInTable(getItemType(), item, getTable());
    }

    @Override
    public Pair<List<Status>, Boolean> queryFeed(String ownerAlias, int limit, String lastItemId) {
        ItemCollection<QueryOutcome> items = null;
        Map<String, AttributeValue> lastEvaluatedKey = null;
        Iterator<Item> iterator = null;
        Item item = null;
        boolean hasMoreItems = true;

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#owner", getOwnerAliasAttr());

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":owner", ownerAlias);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#owner = :owner").withNameMap(nameMap)
                .withValueMap(valueMap);
        querySpec.withScanIndexForward(true);
        querySpec.withMaxResultSize(limit);

        List<Status> feed = new ArrayList<>();

        while(true) {
            try {
                items = getTable().query(querySpec);

                iterator = items.iterator();
                while (iterator.hasNext()) {
                    item = iterator.next();
                    feed.add(extractStatusFromItem(item));
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

        return new Pair<>(feed, hasMoreItems);
    }

    private Status extractStatusFromItem(Item item) {
        return new Status(item.get(getPostAttr()).toString(),
                extractUserFromItem(item),
                item.get(getDateTimeAttr()).toString(),
                (List<String>) item.get(getUrlsAttr()),
                (List<String>) item.get(getMentionsAttr()));
    }

    private User extractUserFromItem(Item item) {
        return new User(item.get(getAuthorFirstNameAttr()).toString(),
                item.get(getAuthorLastNameAttr()).toString(),
                item.get(getAuthorAliasAttr()).toString(),
                item.get(getAuthorImageURLAttr()).toString());
    }

    private PrimaryKey calcPrimaryKey(Map<String, AttributeValue> lastEvaluatedKey) {
        return new PrimaryKey(getOwnerAliasAttr(),
                lastEvaluatedKey.get(getOwnerAliasAttr()).getS(),
                getDateTimeAttr(),
                lastEvaluatedKey.get(getDateTimeAttr()));
    }

    public String getItemType() {
        return itemType;
    }

    public String getOwnerAliasAttr() {
        return ownerAliasAttr;
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

    public String getAuthorFirstNameAttr() {
        return authorFirstNameAttr;
    }

    public String getAuthorLastNameAttr() {
        return authorLastNameAttr;
    }

    public String getAuthorAliasAttr() {
        return authorAliasAttr;
    }

    public String getAuthorImageURLAttr() {
        return authorImageURLAttr;
    }
}

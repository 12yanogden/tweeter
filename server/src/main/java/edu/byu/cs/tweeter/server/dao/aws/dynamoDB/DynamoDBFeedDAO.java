package edu.byu.cs.tweeter.server.dao.aws.dynamoDB;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.util.Pair;

public class DynamoDBFeedDAO extends DynamoDBDAO implements FeedDAO {
    private final String itemType;
    private final String ownerAliasAttr;
    private final String dateTimeAttr;
    private final String postAttr;
    private final String urlsAttr;
    private final String mentionsAttr;
    private final String authorFirstNameAttr;
    private final String authorLastNameAttr;
    private final String authorAliasAttr;
    private final String authorImageURLAttr;

    public DynamoDBFeedDAO(String region) {
        super("feed", region);

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
    public void putItemInFeeds(List<String> ownerAliases, Status status) {
        int MAX_BATCH_SIZE = 25;
        TableWriteItems items = makeBatch();

        for (String ownerAlias: ownerAliases) {
            Item item = new Item()
                    .withPrimaryKey(
                            getOwnerAliasAttr(), ownerAlias,
                            getDateTimeAttr(), status.getDate()
                    )
                    .withString(
                            getAuthorFirstNameAttr(), status.getUser().getFirstName()
                    )
                    .withString(
                            getAuthorLastNameAttr(), status.getUser().getLastName())

                    .withString(
                            getAuthorAliasAttr(), status.getUser().getAlias()
                    )
                    .withString(
                            getAuthorImageURLAttr(), status.getUser().getImageUrl()
                    )
                    .withString(
                            getPostAttr(), status.getPost()
                    )
                    .withList(
                            getMentionsAttr(), status.getMentions()
                    )
                    .withList(
                            getUrlsAttr(), status.getUrls()
                    );

            items.addItemToPut(item);

            if (items.getItemsToPut() != null && items.getItemsToPut().size() == MAX_BATCH_SIZE) {
                getDynamoDB().writeBatch("status", items);
                items = makeBatch();
            }
        }
    }

    @Override
    public Pair<List<Status>, Boolean> queryFeed(String ownerAlias, int limit, Pair<String, String> lastItemId) {
        ItemCollection<QueryOutcome> items;
        Iterator<Item> iterator;
        Item item;
        boolean hasMoreItems = true;
        QuerySpec querySpec = makeQuerySpec(ownerAlias, limit, lastItemId);
        List<Status> feed = new ArrayList<>();

        try {
            items = getTable().query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                feed.add(extractStatusFromItem(item));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (feed.size() < limit) {
            hasMoreItems = false;
        }

        return new Pair<>(feed, hasMoreItems);
    }

    private QuerySpec makeQuerySpec(String ownerAlias, int limit, Pair<String, String> lastItemId) {
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#user", getOwnerAliasAttr());

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":user", ownerAlias);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#user = :user").withNameMap(nameMap)
                .withValueMap(valueMap);
        querySpec.withScanIndexForward(false);
        querySpec.withMaxResultSize(limit);

        System.out.println("ownerAlias: " + lastItemId.getFirst());
        System.out.println("dateTime: " + lastItemId.getSecond());

        if (lastItemId.getFirst() != null && lastItemId.getSecond() != null) {
            querySpec.withExclusiveStartKey(pairToKey(lastItemId));
        }

        return querySpec;
    }

    private PrimaryKey pairToKey(Pair<String, String> pair) {
        return new PrimaryKey(getOwnerAliasAttr(),
                pair.getFirst(),
                getDateTimeAttr(),
                pair.getSecond());
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

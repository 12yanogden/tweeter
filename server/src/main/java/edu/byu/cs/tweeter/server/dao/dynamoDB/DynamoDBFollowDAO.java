package edu.byu.cs.tweeter.server.dao.dynamoDB;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.util.FakeData;

/**
 * A DAO for accessing 'following' data from the database.
 */
public class DynamoDBFollowDAO extends DynamoDBPagedDAO<User> implements FollowDAO {
    private DynamoDBUserDAO userDAO;
    private Table followTable;

    public DynamoDBFollowDAO() {
        this.userDAO = new DynamoDBUserDAO();
        this.followTable = getDynamoDB().getTableByName("follow");
    }

    /**
     * Gets the count of users from the database that the user specified is following. The
     * current implementation uses generated data and doesn't actually access a database.
     *
     * @param alias the User whose count of how many following is desired.
     * @return said count.
     */
    public int getFollowerCount(String alias) {
        return userDAO.getCount(alias, "followerCount");
    }

    public int getFollowingCount(String alias) {
        return userDAO.getCount(alias, "followingCount");
    }

    @Override
    public void follow(String followeeAlias, String followerAlias) {
        Item item = new Item()
                .withPrimaryKey(
                        "followeeAlias",
                        followeeAlias,
                        "followerAlias",
                        followerAlias);

        getDynamoDB().putItemInTable("follow relationship", item, getFollowTable());
    }

    @Override
    public void unfollow(String followeeAlias, String followerAlias) {
        DeleteItemSpec spec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("followeeAlias", followeeAlias, "followerAlias", followerAlias));

        getDynamoDB().deleteItemFromTable("follow relationship", spec, getFollowTable());
    }

    @Override
    public boolean isFollower(String followeeAlias, String followerAlias) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("followeeAlias", followeeAlias, "followerAlias", followerAlias);
        boolean isFollower = true;

        try {
            getDynamoDB().getItemFromTable("follow relationship", spec, getFollowTable());
        } catch (RuntimeException e) {
            if (e.getMessage().equals("[Server Error] Get follow relationship failed")) {
                isFollower = false;
            }
        }

        return isFollower;
    }

    @Override
    protected List<User> getAllItems() {
        return getFakeData().getFakeUsers();
    }

    @Override
    protected String getItemId(User item) {
        return item == null ? null : item.getAlias();
    }

    public Table getFollowTable() {
        return followTable;
    }

    protected FakeData getFakeData() {
        return new FakeData();
    }
}

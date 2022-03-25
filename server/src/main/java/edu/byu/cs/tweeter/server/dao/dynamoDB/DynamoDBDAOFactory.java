package edu.byu.cs.tweeter.server.dao.dynamoDB;

import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class DynamoDBDAOFactory implements DAOFactory {
    @Override
    public AuthTokenDAO makeAuthTokenDAO() {
        return new DynamoDBAuthTokenDAO();
    }

    @Override
    public FollowDAO makeFollowDAO() {
        return new DynamoDBFollowDAO();
    }

    @Override
    public StoryDAO makeStoryDAO() {
        return new DynamoDBStoryDAO();
    }

    @Override
    public FeedDAO makeFeedDAO() {
        return new DynamoDBFeedDAO();
    }

    @Override
    public UserDAO makeUserDAO() {
        return new DynamoDBUserDAO();
    }
}

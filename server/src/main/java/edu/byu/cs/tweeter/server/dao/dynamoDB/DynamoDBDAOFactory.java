package edu.byu.cs.tweeter.server.dao.dynamoDB;

import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.StatusDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class DynamoDBDAOFactory implements DAOFactory {
    @Override
    public FollowDAO makeFollowDAO() {
        return new DynamoDBFollowDAO();
    }

    @Override
    public StatusDAO makeStatusDAO() {
        return new DynamoDBStatusDAO();
    }

    @Override
    public UserDAO makeUserDAO() {
        return new DynamoDBUserDAO();
    }
}

package edu.byu.cs.tweeter.server.dao.dynamoDB;

import edu.byu.cs.tweeter.server.dao.DAO;
import edu.byu.cs.tweeter.server.dao.DAOFactory;

public class DynamoDBDAOFactory extends DAOFactory {
    @Override
    public DAO makeFollowDAO() {
        return new DynamoDBFollowDAO();
    }

    @Override
    public DAO makeStatusDAO() {
        return new DynamoDBStatusDAO();
    }

    @Override
    public DAO makeUserDAO() {
        return new DynamoDBUserDAO();
    }
}

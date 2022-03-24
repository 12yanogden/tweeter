package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class UserService extends AuthTokenService {
    private UserDAO userDAO;

    public UserService(DAOFactory factory) {
        super(factory);

        userDAO = factory.makeUserDAO();
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }
}

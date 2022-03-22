package edu.byu.cs.tweeter.server.lambda.user;

import edu.byu.cs.tweeter.server.lambda.ValidationHandler;
import edu.byu.cs.tweeter.server.service.UserService;

public class UserServiceHandler extends ValidationHandler {
    @Override
    protected UserService getService() {
        return new UserService(getFactory());
    }
}

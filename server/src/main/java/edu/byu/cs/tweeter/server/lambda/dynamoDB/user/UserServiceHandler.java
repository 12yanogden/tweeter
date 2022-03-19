package edu.byu.cs.tweeter.server.lambda.dynamoDB.user;

import edu.byu.cs.tweeter.server.lambda.dynamoDB.ServiceHandler;
import edu.byu.cs.tweeter.server.service.UserService;

public class UserServiceHandler extends ServiceHandler {
    @Override
    protected UserService getService() {
        return new UserService(getFactory());
    }
}

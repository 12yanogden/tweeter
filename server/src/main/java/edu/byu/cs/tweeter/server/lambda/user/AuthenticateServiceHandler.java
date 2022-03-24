package edu.byu.cs.tweeter.server.lambda.user;

import edu.byu.cs.tweeter.server.lambda.ValidationHandler;
import edu.byu.cs.tweeter.server.service.AuthenticateService;
import edu.byu.cs.tweeter.server.service.UserService;

public class AuthenticateServiceHandler extends ValidationHandler {
    @Override
    protected AuthenticateService getService() {
        return new AuthenticateService(getFactory());
    }
}

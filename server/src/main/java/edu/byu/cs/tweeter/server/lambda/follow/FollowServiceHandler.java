package edu.byu.cs.tweeter.server.lambda.follow;

import edu.byu.cs.tweeter.server.lambda.ValidationHandler;
import edu.byu.cs.tweeter.server.service.FollowService;

public class FollowServiceHandler extends ValidationHandler {
    @Override
    protected FollowService getService() {
        return new FollowService(getFactory());
    }
}

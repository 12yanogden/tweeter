package edu.byu.cs.tweeter.server.lambda.dynamoDB.follow;

import edu.byu.cs.tweeter.server.lambda.dynamoDB.ServiceHandler;
import edu.byu.cs.tweeter.server.service.FollowService;

public class FollowServiceHandler extends ServiceHandler {
    @Override
    protected FollowService getService() {
        return new FollowService(getFactory());
    }
}

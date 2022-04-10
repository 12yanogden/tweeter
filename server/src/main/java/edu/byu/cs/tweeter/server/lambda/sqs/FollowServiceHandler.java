package edu.byu.cs.tweeter.server.lambda.sqs;

import edu.byu.cs.tweeter.server.lambda.ServiceHandler;
import edu.byu.cs.tweeter.server.service.FollowService;

public abstract class FollowServiceHandler extends ServiceHandler {
    @Override
    protected FollowService getService() {
        return new FollowService(getFactory());
    }
}

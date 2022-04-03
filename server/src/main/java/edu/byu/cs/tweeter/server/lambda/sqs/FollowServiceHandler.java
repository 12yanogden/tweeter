package edu.byu.cs.tweeter.server.lambda.sqs;

import edu.byu.cs.tweeter.server.service.FollowService;

public abstract class FollowServiceHandler extends SQSHandler {
    @Override
    protected FollowService getService() {
        return new FollowService(getFactory());
    }
}

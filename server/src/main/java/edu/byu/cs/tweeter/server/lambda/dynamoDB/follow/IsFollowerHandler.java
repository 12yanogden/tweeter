package edu.byu.cs.tweeter.server.lambda.dynamoDB.follow;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.server.service.FollowService;

public class IsFollowerHandler extends FollowServiceHandler implements RequestHandler<FollowRequest, IsFollowerResponse> {
    @Override
    public IsFollowerResponse handleRequest(FollowRequest input, Context context) {
        return getService().isFollower(input);
    }
}

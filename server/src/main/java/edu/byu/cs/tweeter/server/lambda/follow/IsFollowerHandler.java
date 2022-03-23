package edu.byu.cs.tweeter.server.lambda.follow;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;

public class IsFollowerHandler extends FollowServiceHandler implements RequestHandler<FollowRequest, IsFollowerResponse> {
    @Override
    public IsFollowerResponse handleRequest(FollowRequest input, Context context) {
        validateAlias("follower alias", input.getTargetUserAlias());
        validateAlias("followee alias", input.getFolloweeAlias());

        return getService().isFollower(input);
    }
}

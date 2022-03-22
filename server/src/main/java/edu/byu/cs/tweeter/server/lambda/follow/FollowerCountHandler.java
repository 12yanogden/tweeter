package edu.byu.cs.tweeter.server.lambda.follow;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.FollowCountRequest;
import edu.byu.cs.tweeter.model.net.response.FollowCountResponse;

public class FollowerCountHandler extends FollowServiceHandler implements RequestHandler<FollowCountRequest, FollowCountResponse> {
    @Override
    public FollowCountResponse handleRequest(FollowCountRequest input, Context context) {
        validateUsername("target user alias", input.getTargetUserAlias());

        return getService().getFollowerCount(input);
    }
}

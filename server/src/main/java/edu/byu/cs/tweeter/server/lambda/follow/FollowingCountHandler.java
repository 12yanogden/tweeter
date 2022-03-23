package edu.byu.cs.tweeter.server.lambda.follow;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.FollowCountRequest;
import edu.byu.cs.tweeter.model.net.response.FollowCountResponse;

public class FollowingCountHandler extends FollowServiceHandler implements RequestHandler<FollowCountRequest, FollowCountResponse> {
    @Override
    public FollowCountResponse handleRequest(FollowCountRequest input, Context context) {
        validateAlias("target user alias", input.getTargetUserAlias());

        return getService().getFollowingCount(input);
    }
}

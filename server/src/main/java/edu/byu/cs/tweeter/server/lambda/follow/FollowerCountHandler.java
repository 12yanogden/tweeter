package edu.byu.cs.tweeter.server.lambda.follow;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.FollowCountRequest;
import edu.byu.cs.tweeter.model.net.response.FollowCountResponse;

public class FollowerCountHandler extends FollowServiceHandler implements RequestHandler<FollowCountRequest, FollowCountResponse> {
    @Override
    public FollowCountResponse handleRequest(FollowCountRequest request, Context context) {
        validateAlias("target user alias", request.getTargetUserAlias());

        FollowCountResponse response = getService().getFollowerCount(request);

        System.out.println("isSuccess: " + response.isSuccess());
        System.out.println("count: " + response.getCount());

        return response;
    }
}

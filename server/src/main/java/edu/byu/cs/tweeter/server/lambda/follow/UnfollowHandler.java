package edu.byu.cs.tweeter.server.lambda.follow;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.response.Response;

public class UnfollowHandler extends FollowServiceHandler implements RequestHandler<FollowRequest, Response> {
    @Override
    public Response handleRequest(FollowRequest request, Context context) {
        Response response = getService().unfollow(request);

        System.out.println("isSuccess: " + response.isSuccess());

        return response;
    }
}

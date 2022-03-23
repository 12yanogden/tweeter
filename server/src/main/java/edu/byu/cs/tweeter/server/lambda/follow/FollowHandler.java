package edu.byu.cs.tweeter.server.lambda.follow;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.response.Response;

public class FollowHandler extends FollowServiceHandler implements RequestHandler<FollowRequest, Response> {
    @Override
    public Response handleRequest(FollowRequest input, Context context) {
        validateAlias("follower alias", input.getTargetUserAlias());
        validateAlias("followee alias", input.getFolloweeAlias());

        return getService().follow(input);
    }
}

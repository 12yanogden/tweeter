package edu.byu.cs.tweeter.server.lambda.dynamoDB.follow;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.service.FollowService;

public class FollowHandler extends FollowServiceHandler implements RequestHandler<FollowRequest, Response> {
    @Override
    public Response handleRequest(FollowRequest input, Context context) {
        return getService().follow(input);
    }
}

package edu.byu.cs.tweeter.server.lambda.dynamoDB.status;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.service.StatusService;

public class PostStatusHandler extends StatusServiceHandler implements RequestHandler<PostStatusRequest, Response> {
    @Override
    public Response handleRequest(PostStatusRequest input, Context context) {
        return getService().postStatus(input);
    }
}

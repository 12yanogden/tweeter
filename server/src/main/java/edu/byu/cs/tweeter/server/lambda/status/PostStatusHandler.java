package edu.byu.cs.tweeter.server.lambda.status;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;
import edu.byu.cs.tweeter.model.net.response.Response;

public class PostStatusHandler extends StatusServiceHandler implements RequestHandler<PostStatusRequest, Response> {
    @Override
    public Response handleRequest(PostStatusRequest request, Context context) {
        validateAlias("target user alias", request.getStatus().getUser().getAlias());

        Response response = getService().postStatus(request);

        System.out.println("isSuccess: " + response.isSuccess());

        return response;
    }
}

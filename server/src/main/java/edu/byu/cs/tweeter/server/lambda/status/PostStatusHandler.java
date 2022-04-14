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
        System.out.println("Enter handleRequest");
        validateAlias("target user alias", request.getStatus().getUser().getAlias());

        System.out.println("request.authToken: " + request.getAuthToken().toString());
        System.out.println("request.status: " + request.getStatus().toString());

        Response response = getService().postStatus(request);

        System.out.println("isSuccess: " + response.isSuccess());

        return response;
    }
}

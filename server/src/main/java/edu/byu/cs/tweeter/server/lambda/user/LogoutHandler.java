package edu.byu.cs.tweeter.server.lambda.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.AuthenticatedRequest;
import edu.byu.cs.tweeter.model.net.request.Request;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.Response;

public class LogoutHandler extends AuthenticateServiceHandler implements RequestHandler<AuthenticatedRequest, Response> {
    @Override
    public Response handleRequest(AuthenticatedRequest request, Context context) {
        Response response = getService().logout(request);

        System.out.println("isSuccess: " + response.isSuccess());

        return response;
    }
}

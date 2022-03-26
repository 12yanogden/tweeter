package edu.byu.cs.tweeter.server.lambda.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.Response;

public class GetUserHandler extends AuthenticateServiceHandler implements RequestHandler<GetUserRequest, GetUserResponse> {
    @Override
    public GetUserResponse handleRequest(GetUserRequest request, Context context) {
        validateAlias("user alias", request.getUserAlias());

        GetUserResponse response = getService().getUser(request);

        System.out.println("isSuccess: " + response.isSuccess());
        System.out.println("user: " + response.getUser());

        return response;
    }
}

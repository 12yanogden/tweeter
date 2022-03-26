package edu.byu.cs.tweeter.server.lambda.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.AuthenticateResponse;

public class RegisterHandler extends AuthenticateServiceHandler implements RequestHandler<RegisterRequest, AuthenticateResponse> {
    @Override
    public AuthenticateResponse handleRequest(RegisterRequest request, Context context) {
        validateAlias("alias", request.getAlias());
        validateNotNull("password", request.getPassword());
        validateNotNull("first name", request.getFirstName());
        validateNotNull("last name", request.getLastName());
        validateNotNull("image", request.getImage());

        AuthenticateResponse response = getService().register(request);

        System.out.println("isSuccess: " + response.isSuccess());

        return response;
    }
}

package edu.byu.cs.tweeter.server.lambda.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.AuthenticateResponse;

public class RegisterHandler extends UserServiceHandler implements RequestHandler<RegisterRequest, AuthenticateResponse> {
    @Override
    public AuthenticateResponse handleRequest(RegisterRequest input, Context context) {
        validateUsername("username", input.getUsername());
        validateNotNull("password", input.getPassword());
        validateNotNull("first name", input.getFirstName());
        validateNotNull("last name", input.getLastName());
        validateNotNull("image", input.getImage());

        return getService().register(input);
    }
}

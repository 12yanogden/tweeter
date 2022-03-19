package edu.byu.cs.tweeter.server.lambda.dynamoDB.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.AuthenticateResponse;
import edu.byu.cs.tweeter.server.service.UserService;

public class RegisterHandler extends UserServiceHandler implements RequestHandler<RegisterRequest, AuthenticateResponse> {
    @Override
    public AuthenticateResponse handleRequest(RegisterRequest input, Context context) {
        return getService().register(input);
    }
}

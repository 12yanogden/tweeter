package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.AuthenticateRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.server.service.UserService;

/**
 * An AWS lambda function that logs a user in and returns the user object and an auth code for
 * a successful login.
 */
public class LoginHandler implements RequestHandler<AuthenticateRequest, LoginResponse> {
    @Override
    public LoginResponse handleRequest(AuthenticateRequest authenticateRequest, Context context) {
        UserService userService = new UserService();
        return userService.login(authenticateRequest);
    }
}

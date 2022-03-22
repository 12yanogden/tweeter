package edu.byu.cs.tweeter.server.lambda.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.AuthenticateRequest;
import edu.byu.cs.tweeter.model.net.response.AuthenticateResponse;

/**
 * An AWS lambda function that logs a user in and returns the user object and an auth code for
 * a successful login.
 */
public class LoginHandler extends UserServiceHandler implements RequestHandler<AuthenticateRequest, AuthenticateResponse> {
    @Override
    public AuthenticateResponse handleRequest(AuthenticateRequest request, Context context) {
        validateUsername("username", request.getUsername());
        validateNotNull(" password", request.getPassword());

        return getService().login(request);
    }
}

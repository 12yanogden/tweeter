package edu.byu.cs.tweeter.server.lambda.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.AuthenticateRequest;
import edu.byu.cs.tweeter.model.net.response.AuthenticateResponse;

/**
 * An AWS lambda function that logs a user in and returns the user object and an auth code for
 * a successful login.
 */
public class LoginHandler extends AuthenticateServiceHandler implements RequestHandler<AuthenticateRequest, AuthenticateResponse> {
    @Override
    public AuthenticateResponse handleRequest(AuthenticateRequest request, Context context) {
        validateAlias("alias", request.getAlias());
        validateNotNull(" password", request.getPassword());

        System.out.println("alias: "+ request.getAlias());
        System.out.println("password: "+ request.getPassword());

        AuthenticateResponse response = getService().login(request);

        System.out.println("isSuccess: " + response.isSuccess());

        return response;
    }
}

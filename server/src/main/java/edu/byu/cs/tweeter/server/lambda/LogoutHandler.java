package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.Request;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.service.UserService;

public class LogoutHandler implements RequestHandler<Request, Response> {
    @Override
    public Response handleRequest(Request input, Context context) {
        UserService userService = new UserService();

        return userService.logout(input);
    }
}

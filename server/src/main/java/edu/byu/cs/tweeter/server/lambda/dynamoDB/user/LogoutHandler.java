package edu.byu.cs.tweeter.server.lambda.dynamoDB.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.Request;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.service.UserService;

public class LogoutHandler extends UserServiceHandler implements RequestHandler<Request, Response> {
    @Override
    public Response handleRequest(Request input, Context context) {
        return getService().logout(input);
    }
}

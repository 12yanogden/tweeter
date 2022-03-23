package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.AuthenticateRequest;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.request.Request;
import edu.byu.cs.tweeter.model.net.response.AuthenticateResponse;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.dao.dynamoDB.DynamoDBUserDAO;
import edu.byu.cs.tweeter.util.Pair;

public class UserService extends FactoryService {
    public UserService(DAOFactory factory) {
        super(factory);
    }

    public AuthenticateResponse register(RegisterRequest input) {
        Pair<User, AuthToken> authentication = getDAO().register(input.getFirstName(), input.getLastName(), input.getAlias(), input.getPassword(), input.getImage());

        return new AuthenticateResponse(authentication.getFirst(), authentication.getSecond());
    }

    public AuthenticateResponse login(AuthenticateRequest request) {
        Pair<User, AuthToken> authentication = getDAO().login(request.getAlias(), request.getPassword());

        return new AuthenticateResponse(authentication.getFirst(), authentication.getSecond());
    }

    public GetUserResponse getUser(GetUserRequest request) {
        return new GetUserResponse(getDAO().getUser(request.getUserAlias()));
    }

    public Response logout(Request request) {
        return new Response(true);
    }

    protected UserDAO getDAO() {
        return factory.makeUserDAO();
    }
}

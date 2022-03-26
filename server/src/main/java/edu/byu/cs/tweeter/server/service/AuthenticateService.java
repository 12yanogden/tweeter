package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.AuthenticateRequest;
import edu.byu.cs.tweeter.model.net.request.AuthenticatedRequest;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.request.Request;
import edu.byu.cs.tweeter.model.net.response.AuthenticateResponse;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.DAOFactory;

public class AuthenticateService extends UserService {
    public AuthenticateService(DAOFactory factory) {
        super(factory);
    }

    public AuthenticateResponse register(RegisterRequest request) {
        User user = extractUserFromRequest(request);
        String imageURL = getUserDAO().putUser(user, request.getPassword(), request.getImage());

        user.setImageUrl(imageURL);

        return new AuthenticateResponse(user, makeAuthToken());
    }

    public AuthenticateResponse login(AuthenticateRequest request) {
        AuthenticateResponse response;

        User user = getUserDAO().getUser(request.getAlias(), request.getPassword());

        System.out.println("user != null: " + (user != null));
        if (user != null) {
            response = new AuthenticateResponse(user, makeAuthToken());

            System.out.println("isSuccess: " + response.isSuccess());

        } else {
            response = new AuthenticateResponse("Invalid password");
        }

        System.out.println("exit login");
        return response;
    }

    public GetUserResponse getUser(GetUserRequest request) {
        GetUserResponse response;

        if (validateAuthToken(request.getAuthToken())) {
            response = new GetUserResponse(getUserDAO().getUser(request.getUserAlias()));
        } else {
            response = new GetUserResponse(getInvalidTokenMsg());
        }

        return response;
    }

    public Response logout(AuthenticatedRequest request) {
        getAuthTokenDAO().deleteAuthToken(request.getAuthToken().getToken());

        return new Response(true);
    }

    private User extractUserFromRequest(RegisterRequest request) {
        return new User(request.getFirstName(), request.getLastName(), request.getAlias(), "");
    }

    private AuthToken makeAuthToken() {
        AuthToken authToken = new AuthToken();

        getAuthTokenDAO().putAuthToken(authToken);

        System.out.println("token: " + authToken.getToken());
        System.out.println("dateTime: " + authToken.getDatetime());
        System.out.println("exit makeAuthToken");

        return authToken;
    }
}

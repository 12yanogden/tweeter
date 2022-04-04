package edu.byu.cs.tweeter.server.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.AuthenticateRequest;
import edu.byu.cs.tweeter.model.net.request.AuthenticatedRequest;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.AuthenticateResponse;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.BucketDAO;
import edu.byu.cs.tweeter.server.dao.DAOFactory;

public class AuthenticateService extends UserService {
    private final BucketDAO bucketDAO;

    public AuthenticateService(DAOFactory factory) {
        super(factory);

        bucketDAO = factory.makeBucketDAO();
    }

    public AuthenticateResponse register(RegisterRequest request) {
        User user = extractUserFromRequest(request);
        String imageName = user.getAlias().substring(1) + ".png";
        String imageURL = getBucketDAO().getBucketURL() + "/" + imageName;

        user.setImageUrl(imageURL);

        getBucketDAO().putImage(imageName, request.getImage());

        getUserDAO().putUser(user, hash(request.getPassword()));

        return new AuthenticateResponse(user, makeAuthToken());
    }

    public AuthenticateResponse login(AuthenticateRequest request) {
        AuthenticateResponse response;

        User user = getUserDAO().getUser(request.getAlias(), hash(request.getPassword()));

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

    private String hash(String password) {
        byte[] bytes;
        StringBuilder stringBuilder = new StringBuilder();
        String failedMsg = "FAILED TO HASH";
        String out = failedMsg;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(password.getBytes());

            bytes = md.digest();

            for (byte aByte : bytes) {
                stringBuilder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            out = stringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (out.equals(failedMsg)) {
            throw new RuntimeException("[Server Error] Failed to hash: " + password);
        }

        return out;
    }

    public BucketDAO getBucketDAO() {
        return bucketDAO;
    }
}

package edu.byu.cs.tweeter.server.service;

import com.google.gson.Gson;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusQueue2Request;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.FollowCountResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.PagedUserResponse;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowService extends UserService {
    private final FollowDAO followDAO;
    private final UserDAO userDAO;
    private PostStatusQueuesService postStatusQueuesService;

    public FollowService(DAOFactory factory) {
        super(factory);

        followDAO = factory.makeFollowDAO();
        userDAO = factory.makeUserDAO();
    }

    public FollowCountResponse getFollowerCount(FollowCountRequest request) {
        FollowCountResponse response;

        if (validateAuthToken(request.getAuthToken())) {
            response = new FollowCountResponse(getUserDAO().getFollowerCount(request.getTargetUserAlias()));
        } else {
            response = new FollowCountResponse(getInvalidTokenMsg());
        }

        return response;
    }

    public FollowCountResponse getFollowingCount(FollowCountRequest request) {
        FollowCountResponse response;

        if (validateAuthToken(request.getAuthToken())) {
            response = new FollowCountResponse(getUserDAO().getFollowingCount(request.getTargetUserAlias()));
        } else {
            response = new FollowCountResponse(getInvalidTokenMsg());
        }

        return response;
    }

    public IsFollowerResponse isFollower(FollowRequest request) {
        IsFollowerResponse response;

        if (validateAuthToken(request.getAuthToken())) {
            response = new IsFollowerResponse(getFollowDAO().isFollower(request.getFollowee().getAlias(), request.getFollower().getAlias()));
        } else {
            response = new IsFollowerResponse(getInvalidTokenMsg());
        }

        return response;
    }

    public PagedUserResponse getPagedFollowing(PagedRequest request) {
        PagedUserResponse response;

        if (validateAuthToken(request.getAuthToken())) {
            Pair<String, String> lastItemId = new Pair<>(request.getAlias(), request.getDateTime());
            Pair<List<User>, Boolean> queryResponse = getFollowDAO().queryFollowing(request.getTargetUserAlias(), request.getLimit(), lastItemId);

            response = new PagedUserResponse(queryResponse.getFirst(), queryResponse.getSecond());
        } else {
            response = new PagedUserResponse(getInvalidTokenMsg());
        }

        return response;
    }

    public PagedUserResponse getPagedFollowers(PagedRequest request) {
        PagedUserResponse response;

        if (validateAuthToken(request.getAuthToken())) {
            Pair<String, String> lastItemId = new Pair<>(request.getAlias(), request.getDateTime());
            Pair<List<User>, Boolean> queryResponse = getFollowDAO().queryFollowers(request.getTargetUserAlias(), request.getLimit(), lastItemId);

            response = new PagedUserResponse(queryResponse.getFirst(), queryResponse.getSecond());
        } else {
            response = new PagedUserResponse(getInvalidTokenMsg());
        }

        return response;
    }

    public void toPostStatusQueue2(PostStatusRequest request) {
        List<String> followers = getFollowDAO().getFollowerAliases(request.getStatus().getUser().getAlias());
        PostStatusQueue2Request queue2Request = new PostStatusQueue2Request(request.getStatus(), followers);
        String queue2Json = new Gson().toJson(queue2Request);

        getPostStatusQueuesService().getPostStatusQueue2DAO().sendToQueue(queue2Json);
    }

    public Response follow(FollowRequest request) {
        Response response;

        if (validateAuthToken(request.getAuthToken())) {
            getFollowDAO().putItem(request.getFollowee(), request.getFollower());
            getUserDAO().incrementFollowingCount(request.getFollower().getAlias());
            getUserDAO().incrementFollowerCount(request.getFollowee().getAlias());

            response = new Response(true);
        } else {
            response = new Response(false);
        }

        return response;
    }

    public Response unfollow(FollowRequest request) {
        Response response;

        if (validateAuthToken(request.getAuthToken())) {
            getFollowDAO().deleteItem(request.getFollowee().getAlias(), request.getFollower().getAlias());
            getUserDAO().decrementFollowingCount(request.getFollower().getAlias());
            getUserDAO().decrementFollowerCount(request.getFollowee().getAlias());

            response = new Response(true);
        } else {
            response = new Response(false);
        }

        return response;
    }

    public FollowDAO getFollowDAO() {
        return followDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public PostStatusQueuesService getPostStatusQueuesService() {
        if (postStatusQueuesService == null) {
            postStatusQueuesService = new PostStatusQueuesService(getFactory());
        }

        return postStatusQueuesService;
    }
}

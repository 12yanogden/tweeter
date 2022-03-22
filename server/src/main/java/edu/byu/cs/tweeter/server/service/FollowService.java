package edu.byu.cs.tweeter.server.service;

import java.util.Random;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.FollowCountResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.dynamoDB.DynamoDBFollowDAO;
import edu.byu.cs.tweeter.server.dao.dynamoDB.DynamoDBPagedDAO;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowService extends PagedService<User> {
    public FollowService(DAOFactory factory) {
        super(factory);
    }

    public FollowCountResponse getFollowerCount(FollowCountRequest request) {
        int followCount = getDAO().getFollowCount(request.getTargetUserAlias());

        return new FollowCountResponse(followCount);
    }

    public FollowCountResponse getFollowingCount(FollowCountRequest request) {
        int followCount = getDAO().getFollowCount(request.getTargetUserAlias());

        return new FollowCountResponse(followCount);
    }

    public IsFollowerResponse isFollower(FollowRequest request) {
        boolean isFollower = new Random().nextInt() > 0;

        return new IsFollowerResponse(isFollower);
    }

    public Response follow(FollowRequest request) {
        return new Response(true);
    }

    public Response unfollow(FollowRequest request) {
        return new Response(true);
    }

    protected FollowDAO getDAO() {
        return factory.makeFollowDAO();
    }
}

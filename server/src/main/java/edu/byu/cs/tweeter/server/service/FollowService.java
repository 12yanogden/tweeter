package edu.byu.cs.tweeter.server.service;

import java.util.Random;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowCountResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.dynamoDB.DynamoDBFollowDAO;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowService extends PagedService<User> {
    public FollowCountResponse getFollowCount(FollowCountRequest request) {
        if (request.getTargetUserAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a targetUser alias");
        }

        int followCount = getDAO().getFollowCount(request.getTargetUserAlias());

        return new FollowCountResponse(followCount);
    }

    public IsFollowerResponse isFollower(FollowRequest request) {
        if (request.getTargetUserAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a targetUser alias");
        }

        if (request.getFolloweeAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a followee alias");
        }

        boolean isFollower = new Random().nextInt() > 0;

        return new IsFollowerResponse(isFollower);
    }

    @Override
    public DynamoDBFollowDAO getDAO() {
        return new DynamoDBFollowDAO();
    }

    public Response follow(FollowRequest request) {
        if (request.getTargetUserAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a targetUser alias");
        }

        if (request.getFolloweeAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a followee alias");
        }

        return new Response(true);
    }

    public Response unfollow(FollowRequest request) {
        if (request.getTargetUserAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a targetUser alias");
        }

        if (request.getFolloweeAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a followee alias");
        }

        return new Response(true);
    }
}

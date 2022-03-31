package edu.byu.cs.tweeter.server.service;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PagedStatusResponse;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.util.Pair;

public class StatusService extends AuthTokenService {
    private FeedDAO feedDAO;
    private FollowDAO followDAO;
    private StoryDAO storyDAO;

    public StatusService(DAOFactory factory) {
        super(factory);

        feedDAO = factory.makeFeedDAO();
        followDAO = factory.makeFollowDAO();
        storyDAO = factory.makeStoryDAO();

    }

    public PagedStatusResponse getStory(PagedRequest request) {
        PagedStatusResponse response;

        if (validateAuthToken(request.getAuthToken())) {
            Pair<String, String> lastItemId = new Pair<>(request.getAlias(), request.getDateTime());
            Pair<List<Status>, Boolean> queryResponse = getStoryDAO().queryStory(request.getTargetUserAlias(), request.getLimit(), lastItemId);

            response = new PagedStatusResponse(queryResponse.getFirst(), queryResponse.getSecond());

        } else {
            response = new PagedStatusResponse(getInvalidTokenMsg());
        }

        return response;
    }

    public PagedStatusResponse getFeed(PagedRequest request) {
        PagedStatusResponse response;

        if (validateAuthToken(request.getAuthToken())) {
            Pair<String, String> lastItemId = new Pair<>(request.getAlias(), request.getDateTime());
            Pair<List<Status>, Boolean> queryResponse = getFeedDAO().queryFeed(request.getTargetUserAlias(), request.getLimit(), lastItemId);

            response = new PagedStatusResponse(queryResponse.getFirst(), queryResponse.getSecond());

        } else {
            response = new PagedStatusResponse(getInvalidTokenMsg());
        }

        return response;
    }

    public Response postStatus(PostStatusRequest request) {
        Response response;

        if (validateAuthToken(request.getAuthToken())) {
            getStoryDAO().putItem(request.getStatus());

            List<User> followers = getFollowDAO().getFollowers(request.getStatus().getUser().getAlias());

            for (User follower: followers) {
                getFeedDAO().putItem(follower.getAlias(), request.getStatus());
            }

            response = new Response(true);

        } else {
            response = new Response(false);
        }

        return response;
    }

    public FeedDAO getFeedDAO() {
        return feedDAO;
    }

    public FollowDAO getFollowDAO() {
        return followDAO;
    }

    public StoryDAO getStoryDAO() {
        return storyDAO;
    }
}

package edu.byu.cs.tweeter.server.service;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PagedStatusResponse;
import edu.byu.cs.tweeter.model.net.response.PagedUserResponse;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.StatusDAO;
import edu.byu.cs.tweeter.util.Pair;

public class StatusService extends AuthTokenService {
    private StatusDAO statusDAO;

    public StatusService(DAOFactory factory) {
        super(factory);

        statusDAO = factory.makeStatusDAO();
    }

    public PagedStatusResponse getStory(PagedRequest request) {
        PagedStatusResponse response;

        if (validateAuthToken(request.getAuthToken())) {
            Pair<List<Status>, Boolean> queryResponse = getStatusDAO().queryStory(request.getTargetUserAlias(), request.getLimit(), request.getLastItemId());

            response = new PagedStatusResponse(queryResponse.getFirst(), queryResponse.getSecond());

        } else {
            response = new PagedStatusResponse(getInvalidTokenMsg());
        }

        return response;
    }

    public PagedStatusResponse getFeed(PagedRequest request) {
        PagedStatusResponse response;

        if (validateAuthToken(request.getAuthToken())) {
            Pair<List<Status>, Boolean> queryResponse = getStatusDAO().queryFeed(request.getTargetUserAlias(), request.getLimit(), request.getLastItemId());

            response = new PagedStatusResponse(queryResponse.getFirst(), queryResponse.getSecond());

        } else {
            response = new PagedStatusResponse(getInvalidTokenMsg());
        }

        return response;
    }

    public Response postStatus(PostStatusRequest request) {
        Response response;

        if (validateAuthToken(request.getAuthToken())) {
            getStatusDAO().putItem(request.getStatus());

            response = new Response(true);

        } else {
            response = new Response(false);
        }

        return response;
    }

    protected StatusDAO getStatusDAO() {
        return statusDAO;
    }
}

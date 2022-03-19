package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.StatusDAO;
import edu.byu.cs.tweeter.server.dao.dynamoDB.DynamoDBStatusDAO;

public class StatusService extends PagedService<Status> {
    public StatusService(DAOFactory factory) {
        super(factory);
    }

    public Response postStatus(PostStatusRequest request) {
        return getDAO().postStatus(request);
    }

    protected StatusDAO getDAO() {
        return factory.makeStatusDAO();
    }
}

package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.dynamoDB.DynamoDBStatusDAO;

public class StatusService extends PagedService<Status> {
    public Response postStatus(PostStatusRequest request) {
        return getDAO().postStatus(request);
    }

    public DynamoDBStatusDAO getDAO() {
        return new DynamoDBStatusDAO();
    }
}

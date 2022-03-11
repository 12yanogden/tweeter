package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.PagedDAO;
import edu.byu.cs.tweeter.server.dao.StatusDAO;

public class StatusService extends PagedService<Status> {
    public Response postStatus(PostStatusRequest request) {
        return getDAO().postStatus(request);
    }

    public StatusDAO getDAO() {
        return new StatusDAO();
    }
}

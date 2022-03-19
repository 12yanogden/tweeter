package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.Response;

public interface StatusDAO extends PagedDAO<Status> {
    Response postStatus(PostStatusRequest request);
}

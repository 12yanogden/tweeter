package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.Response;

public class StatusDAO extends PagedDAO<Status> {
    @Override
    protected List<Status> getAllItems() {
        return getFakeData().getFakeStatuses();
    }

    @Override
    protected String getItemId(Status item) {
        return item == null ? null : item.getPost();
    }

    public Response postStatus(PostStatusRequest request) {
        return new Response(true);
    }
}

package edu.byu.cs.tweeter.server.dao.dynamoDB;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.server.dao.StatusDAO;
import edu.byu.cs.tweeter.util.FakeData;

public class DynamoDBStatusDAO extends DynamoDBPagedDAO<Status> implements StatusDAO {
    @Override
    protected List<Status> getAllItems() {
        return getFakeData().getFakeStatuses();
    }

    @Override
    protected String getItemId(Status item) {
        return item == null ? null : item.getPost();
    }

    @Override
    public Response postStatus(PostStatusRequest request) {
        return new Response(true);
    }

    protected FakeData getFakeData() {
        return new FakeData();
    }
}

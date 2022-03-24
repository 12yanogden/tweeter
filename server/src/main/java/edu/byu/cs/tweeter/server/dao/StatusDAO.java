package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.Response;
import edu.byu.cs.tweeter.util.Pair;

public interface StatusDAO extends PagedDAO<Status> {
    void putItem(Status status);
    Pair<List<Status>, Boolean> queryStory(String alias, int limit, String lastItemId);
    Pair<List<Status>, Boolean> queryFeed(String targetUserAlias, int limit, String lastItemId);
}

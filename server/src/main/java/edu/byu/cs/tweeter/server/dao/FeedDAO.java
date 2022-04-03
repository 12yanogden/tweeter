package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.util.Pair;

public interface FeedDAO extends PagedDAO<Status> {
    void putItem(String ownerAlias, Status status);
    Pair<List<Status>, Boolean> queryFeed(String targetUserAlias, int limit, Pair<String, String> lastItemId);
    void putItemInFeeds(List<String> ownerAliases, Status status);
}

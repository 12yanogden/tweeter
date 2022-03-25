package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.util.Pair;

public interface StoryDAO extends PagedDAO<Status> {
    void putItem(Status status);
    Pair<List<Status>, Boolean> queryStory(String alias, int limit, Pair<String, String> lastItemId);
}

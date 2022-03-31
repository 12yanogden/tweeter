package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Handler;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

public class GetFollowingTask extends PagedUserTask {
    public GetFollowingTask(AuthToken authToken, User targetUser, int limit, User lastItem, Handler messageHandler, ServerFacade facade, String urlPath) {
        super(authToken, targetUser, limit, lastItem, messageHandler, facade, urlPath);
    }

    @Override
    protected Pair<String, String> getLastItemId(User follower, User followee) {
        Pair<String, String> lastItemId = new Pair<>(null, null);;

        if (followee != null && follower != null) {
            lastItemId = new Pair<>(followee.getAlias(), follower.getAlias());
        }

        return lastItemId;
    }
}

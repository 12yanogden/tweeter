package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

public class PagedUserTask extends PagedTask<User> {
    public PagedUserTask(AuthToken authToken, User targetUser, int limit, User lastItem, Handler messageHandler, ServerFacade facade, String urlPath) {
        super(authToken, targetUser, limit, lastItem, messageHandler, facade, urlPath);
    }

    @Override
    protected Pair<List<User>, Boolean> getItems() {
        return getFakeData().getPageOfUsers(getLastItem(), getLimit(), getTargetUser());
    }

    protected String getAliasFromUser(User user) {
        return user == null ? null : user.getAlias();
    }
}

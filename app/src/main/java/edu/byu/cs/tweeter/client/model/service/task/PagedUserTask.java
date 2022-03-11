package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Handler;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;
import edu.byu.cs.tweeter.model.net.response.PagedUserResponse;
import edu.byu.cs.tweeter.util.Pair;

public class PagedUserTask extends PagedTask<User> {
    public PagedUserTask(AuthToken authToken, User targetUser, int limit, User lastItem, Handler messageHandler, ServerFacade facade, String urlPath) {
        super(authToken, targetUser, limit, lastItem, messageHandler, facade, urlPath);
    }

    @Override
    protected PagedUserResponse request(PagedRequest request, String urlPath) throws IOException, TweeterRemoteException {
        return getServerFacade().request(request, urlPath, PagedUserResponse.class);
    }

    protected String getLastItemId(User status) {
        return status == null ? null : status.getAlias();
    }
}

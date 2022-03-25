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
import edu.byu.cs.tweeter.model.net.response.PagedStatusResponse;
import edu.byu.cs.tweeter.util.Pair;

public class PagedStatusTask extends PagedTask<Status> {
    public PagedStatusTask(AuthToken authToken, User targetUser, int limit, Status lastItem, Handler messageHandler, ServerFacade facade, String urlPath) {
        super(authToken, targetUser, limit, lastItem, messageHandler, facade, urlPath);
    }

    @Override
    protected PagedStatusResponse request(PagedRequest request, String urlPath) throws IOException, TweeterRemoteException {
        return getServerFacade().request(request, urlPath, PagedStatusResponse.class);
    }

    @Override
    protected Pair<String, String> getLastItemId(User targetUser, Status status) {
        Pair<String, String> lastItemId = null;

        if (status != null) {
            lastItemId = new Pair<>(status.getUser().getAlias(), status.getDate());
        }

        return lastItemId;
    }
}

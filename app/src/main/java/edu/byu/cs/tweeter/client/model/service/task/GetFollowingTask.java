package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;

/**
 * Background task that retrieves a page of other users being followed by a specified user.
 */
public class GetFollowingTask extends PagedUserTask {
    private static final String LOG_TAG = "GetFollowingTask";
    public static final String FOLLOWEES_KEY = "followees";

    public GetFollowingTask(AuthToken authToken, User targetUser, int limit, User lastFollowee,
                            Handler messageHandler, ServerFacade facade, String urlPath) {
        super(authToken, targetUser, limit, lastFollowee, messageHandler, facade, urlPath);
    }

    @Override
    protected void runTask() throws IOException, TweeterRemoteException {
        String targetUserAlias = getAliasFromUser(getTargetUser());
        String lastItemId = getAliasFromUser(getLastItem());

        PagedRequest request = new PagedRequest(getAuthToken(), targetUserAlias, getLimit(), lastItemId);
        PagedResponse<User> response = getServerFacade().request(request, getUrlPath(), new PagedResponse<>("placeholder"));

        if(response.isSuccess()) {
            setItems(response.getItems());
            setHasMorePages(response.getHasMorePages());
            sendSuccessMessage();

        } else {
            sendFailedMessage(response.getMessage());
        }
    }
}

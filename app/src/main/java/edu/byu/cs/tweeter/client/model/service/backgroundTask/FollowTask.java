package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.inputmethod.InlineSuggestionsRequest;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that establishes a following relationship between two users.
 */
public class FollowTask implements Runnable {
    private static final String LOG_TAG = "FollowTask";

    public static final String SUCCESS_KEY = "success";
    public static final String MESSAGE_KEY = "message";
    public static final String EXCEPTION_KEY = "exception";

    public static final String FOLLOWEE_ALIAS_KEY = "followeeAlias";
    public static final String FOLLOWEE_FIRSTNAME_KEY = "followeeFirstname";
    public static final String FOLLOWEE_LASTNAME_KEY = "followeeLastname";
    public static final String FOLLOWEE_IMAGEURL_KEY = "followeeImageURL";

    /**
     * Auth token for logged-in user.
     * This user is the "follower" in the relationship.
     */
    private AuthToken authToken;
    /**
     * The user that is being followed.
     */
    private User followee;
    /**
     * Message handler that will receive task results.
     */
    private Handler messageHandler;

    public FollowTask(AuthToken authToken, User followee, Handler messageHandler) {
        this.authToken = authToken;
        this.followee = followee;
        this.messageHandler = messageHandler;
    }

    @Override
    public void run() {
        try {

            sendSuccessMessage();

        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
        }
    }

    private void sendSuccessMessage() {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, true);
        msgBundle.putString(FOLLOWEE_ALIAS_KEY, followee.getAlias());
        msgBundle.putString(FOLLOWEE_FIRSTNAME_KEY, followee.getFirstName());
        msgBundle.putString(FOLLOWEE_LASTNAME_KEY, followee.getLastName());
        msgBundle.putString(FOLLOWEE_IMAGEURL_KEY, followee.getImageUrl());

        Message msg = Message.obtain();
        msg.setData(msgBundle);

        messageHandler.sendMessage(msg);
    }

    private void sendFailedMessage(String message) {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, false);
        msgBundle.putString(MESSAGE_KEY, message);

        Message msg = Message.obtain();
        msg.setData(msgBundle);

        messageHandler.sendMessage(msg);
    }

    private void sendExceptionMessage(Exception exception) {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, false);
        msgBundle.putSerializable(EXCEPTION_KEY, exception);

        Message msg = Message.obtain();
        msg.setData(msgBundle);

        messageHandler.sendMessage(msg);
    }
}

package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;

public abstract class BackgroundTask implements Runnable {
    private static final String LOG_TAG = "BackgroundTask";
    public static final String USER_KEY = "user";
    public static final String SUCCESS_KEY = "success";
    public static final String MESSAGE_KEY = "message";
    public static final String EXCEPTION_KEY = "exception";

    protected final Handler messageHandler;
    private final ServerFacade facade;
    private final String urlPath;

    protected BackgroundTask(Handler messageHandler, ServerFacade facade, String urlPath) {
        this.messageHandler = messageHandler;
        this.facade = facade;
        this.urlPath = urlPath;
    }

    @Override
    public void run() {
        try {
            runTask();
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
        }
    }
    // This method is public instead of protected to make it accessible to test cases
    public void sendSuccessMessage() {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, true);
        loadSuccessBundle(msgBundle);
        sendMessage(msgBundle);
    }

    // To be overridden by each task to add information to the bundle
    protected abstract void loadSuccessBundle(Bundle msgBundle);

    // This method is public instead of protected to make it accessible to test cases
    public void sendFailedMessage(String message) {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, false);
        msgBundle.putString(MESSAGE_KEY, message);
        sendMessage(msgBundle);
    }

    // This method is public instead of protected to make it accessible to test cases
    public void sendExceptionMessage(Exception exception) {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, false);
        msgBundle.putSerializable(EXCEPTION_KEY, exception);
        sendMessage(msgBundle);
    }

    private void sendMessage(Bundle msgBundle) {
        Message msg = Message.obtain();
        msg.setData(msgBundle);

        messageHandler.sendMessage(msg);
    }

    protected abstract void runTask() throws IOException, TweeterRemoteException;

    public ServerFacade getServerFacade() {
        return facade;
    }

    public String getUrlPath() {
        return urlPath;
    }
}
package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.Serializable;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class UserTask extends AuthenticatedTask {
    public UserTask(AuthToken authToken, Handler messageHandler) {
        super(authToken, messageHandler);
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, getUser());
    }

    protected abstract Serializable getUser();
}

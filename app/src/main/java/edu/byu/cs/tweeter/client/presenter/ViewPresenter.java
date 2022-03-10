package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;

public abstract class ViewPresenter {
    public interface View {
        void displayToast(String message);
    }

    private UserService userService;

    public abstract <V extends View> V getView();

    protected UserService getUserService() {
        if (userService == null) {
            userService = new UserService();
        }

        return userService;
    }
}

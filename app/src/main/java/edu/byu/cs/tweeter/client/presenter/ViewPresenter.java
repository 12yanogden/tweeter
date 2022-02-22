package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;

public abstract class ViewPresenter {
    public interface View {
        void displayToast(String message);
    }

    protected UserService userService;

    public ViewPresenter() {
        this.userService = new UserService();
    }

    public abstract <V extends View> V getView();
}

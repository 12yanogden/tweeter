package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.presenter.observer.AuthenticateObserver;

public class LoginPresenter extends AuthenticatePresenter {
    public LoginPresenter(AuthenticateView view) {
        super(view);
    }

    public void login(String alias, String password) {
        userService.login(alias, password, new AuthenticateObserver(this, "login"));
    }
}

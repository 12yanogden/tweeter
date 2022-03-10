package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.presenter.observer.AuthenticateObserver;

/**
 * The presenter for the login functionality of the application.
 */
public class LoginPresenter extends AuthenticatePresenter {
    public LoginPresenter(AuthenticateView view) {
        super(view);
    }

    public void login(String alias, String password) {
        getUserService().login(alias, password, new AuthenticateObserver(this, "login"));
    }
}

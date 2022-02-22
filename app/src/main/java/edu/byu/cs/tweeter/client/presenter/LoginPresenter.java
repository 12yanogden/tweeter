package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter {
    public interface View {
        void displayToast(String message);
        void login(User loggedInUser);
    }

    private View view;
    private UserService userService;

    public LoginPresenter(View view) {
        this.view = view;
        userService = new UserService();
    }

    public void validateLogin(String alias, String password) {
        if (alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
    }

    public void login(String alias, String password) {
        userService.login(alias, password, new LoginObserver());
    }

    public class LoginObserver implements UserService.LoginObserver {
        @Override
        public void handleSuccess(User loggedInUser, AuthToken authToken) {
            Cache.getInstance().setCurrUser(loggedInUser);
            Cache.getInstance().setCurrUserAuthToken(authToken);

            view.login(loggedInUser);
        }

        @Override
        public void handleFailure(String message) {
            view.displayToast("Failed to login: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.displayToast("Failed to login because of exception: " + exception.getMessage());
        }
    }
}

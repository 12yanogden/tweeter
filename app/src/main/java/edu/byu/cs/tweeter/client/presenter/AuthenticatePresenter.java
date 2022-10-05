package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class AuthenticatePresenter extends ViewPresenter {
    public interface AuthenticateView extends View {
        void authenticate(User user);
    }

    protected AuthenticateView view;

    public AuthenticatePresenter(AuthenticateView view) {
        this.view = view;

        this.userService = new UserService();
    }

    @Override
    public AuthenticateView getView() {
        return this.view;
    }

    public void validateLogin(String alias, String password) throws IllegalArgumentException {
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
}

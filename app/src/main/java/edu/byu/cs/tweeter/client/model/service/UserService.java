package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService {
    public interface GetUserObserver {
        void handleSuccess(User user);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public void getUser(AuthToken currUserAuthToken, String alias, GetUserObserver getUserObserver) {
        GetUserTask getUserTask = new GetUserTask(currUserAuthToken, alias, new GetUserHandler(getUserObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getUserTask);
    }

    /**
     * Message handler (i.e., observer) for GetUserTask.
     */
    private class GetUserHandler extends Handler {
        private GetUserObserver getUserObserver;

        public GetUserHandler(GetUserObserver getUserObserver) {
            this.getUserObserver = getUserObserver;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(GetUserTask.SUCCESS_KEY);

            if (success) {
                User user = (User) msg.getData().getSerializable(GetUserTask.USER_KEY);

                getUserObserver.handleSuccess(user);

            } else if (msg.getData().containsKey(GetUserTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetUserTask.MESSAGE_KEY);

                getUserObserver.handleFailure(message);

            } else if (msg.getData().containsKey(GetUserTask.EXCEPTION_KEY)) {
                Exception exception = (Exception) msg.getData().getSerializable(GetUserTask.EXCEPTION_KEY);

                getUserObserver.handleException(exception);
            }
        }
    }

    public interface LogoutObserver {
        void handleSuccess();
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public void logout(AuthToken authToken, LogoutObserver logoutObserver) {
        LogoutTask logoutTask = new LogoutTask(authToken, new LogoutHandler(logoutObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(logoutTask);
    }

    private class LogoutHandler extends Handler {
        private LogoutObserver logoutObserver;

        public LogoutHandler(LogoutObserver logoutObserver) {
            this.logoutObserver = logoutObserver;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(LogoutTask.SUCCESS_KEY);

            if (success) {
                logoutObserver.handleSuccess();

            } else if (msg.getData().containsKey(LogoutTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(LogoutTask.MESSAGE_KEY);

                logoutObserver.handleFailure(message);

            } else if (msg.getData().containsKey(LogoutTask.EXCEPTION_KEY)) {
                Exception exception = (Exception) msg.getData().getSerializable(LogoutTask.EXCEPTION_KEY);

                logoutObserver.handleException(exception);
            }
        }
    }

    public interface LoginObserver {
        void handleSuccess(User loggedInUser, AuthToken authToken);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public void login(String alias, String password, LoginObserver loginObserver) {
        LoginTask loginTask = new LoginTask(alias,
                password,
                new LoginHandler(loginObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(loginTask);
    }

    private class LoginHandler extends Handler {
        private LoginObserver loginObserver;

        public LoginHandler(LoginObserver loginObserver) {
            this.loginObserver = loginObserver;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(LoginTask.SUCCESS_KEY);

            if (success) {
                User loggedInUser = (User) msg.getData().getSerializable(LoginTask.USER_KEY);
                AuthToken authToken = (AuthToken) msg.getData().getSerializable(LoginTask.AUTH_TOKEN_KEY);

                loginObserver.handleSuccess(loggedInUser, authToken);

            } else if (msg.getData().containsKey(LoginTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(LoginTask.MESSAGE_KEY);

                loginObserver.handleFailure(message);

            } else if (msg.getData().containsKey(LoginTask.EXCEPTION_KEY)) {
                Exception exception = (Exception) msg.getData().getSerializable(LoginTask.EXCEPTION_KEY);

                loginObserver.handleException(exception);
            }
        }
    }

    public interface RegisterObserver {
        void handleSuccess(User registeredUser, AuthToken authToken);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public void register(String firstName, String lastName, String alias, String password, String imageURL, RegisterObserver registerObserver) {
        RegisterTask registerTask = new RegisterTask(firstName, lastName, alias, password, imageURL, new RegisterHandler(registerObserver));

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(registerTask);
    }

    private class RegisterHandler extends Handler {
        private RegisterObserver registerObserver;

        public RegisterHandler(RegisterObserver registerObserver) {
            this.registerObserver = registerObserver;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(RegisterTask.SUCCESS_KEY);

            if (success) {
                User registeredUser = (User) msg.getData().getSerializable(RegisterTask.USER_KEY);
                AuthToken authToken = (AuthToken) msg.getData().getSerializable(RegisterTask.AUTH_TOKEN_KEY);

                registerObserver.handleSuccess(registeredUser, authToken);

            } else if (msg.getData().containsKey(RegisterTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(RegisterTask.MESSAGE_KEY);

                registerObserver.handleFailure(message);

            } else if (msg.getData().containsKey(RegisterTask.EXCEPTION_KEY)) {
                Exception exception = (Exception) msg.getData().getSerializable(RegisterTask.EXCEPTION_KEY);

                registerObserver.handleException(exception);
            }
        }
    }
}

package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.AuthenticateHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.SimpleHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.UserHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.AuthenticateObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.UserObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;

public class UserService extends ModelService {
    public void getUser(AuthToken currUserAuthToken, String alias, UserObserver observer) {
        GetUserTask getUserTask = new GetUserTask(currUserAuthToken, alias, new UserHandler(observer));

        execute(getUserTask);
    }

    public void logout(AuthToken authToken, SimpleObserver observer) {
        LogoutTask logoutTask = new LogoutTask(authToken, new SimpleHandler(observer));

        execute(logoutTask);
    }

    public void login(String alias, String password, AuthenticateObserver observer) {
        LoginTask loginTask = new LoginTask(alias,
                password,
                new AuthenticateHandler(observer));

        execute(loginTask);
    }

    public void register(String firstName, String lastName, String alias, String password, String imageURL, AuthenticateObserver observer) {
        RegisterTask registerTask = new RegisterTask(firstName, lastName, alias, password, imageURL, new AuthenticateHandler(observer));

        execute(registerTask);
    }

}

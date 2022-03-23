package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.handler.AuthenticateHandler;
import edu.byu.cs.tweeter.client.model.service.handler.SimpleHandler;
import edu.byu.cs.tweeter.client.model.service.handler.UserHandler;
import edu.byu.cs.tweeter.client.model.service.observerInterface.AuthenticateObserverInterface;
import edu.byu.cs.tweeter.client.model.service.observerInterface.SimpleObserverInterface;
import edu.byu.cs.tweeter.client.model.service.observerInterface.UserObserverInterface;
import edu.byu.cs.tweeter.client.model.service.task.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.task.LoginTask;
import edu.byu.cs.tweeter.client.model.service.task.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.task.RegisterTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;

/**
 * Contains the business logic to support the login operation.
 */
public class UserService extends ModelService {
    /**
     * Creates an instance.
     *
     */
    public UserService() {
    }

    public void register(String firstName, String lastName, String alias, String password, String imageURL, AuthenticateObserverInterface observer) {
        execute(getRegisterTask(firstName, lastName, alias, password, imageURL, observer, "/register"));
    }

    public void login(String alias, String password, AuthenticateObserverInterface observer) {
        execute(getLoginTask(alias, password, observer, "/login"));
    }

    public void getUser(AuthToken currUserAuthToken, String alias, UserObserverInterface observer) {
        execute(getGetUserTask(currUserAuthToken, alias, observer, "/getuser"));
    }

    public void logout(AuthToken authToken, SimpleObserverInterface observer) {
        execute(getLogoutTask(authToken, observer, "/logout"));
    }

    RegisterTask getRegisterTask(String firstName, String lastName, String alias, String password, String imageURL, AuthenticateObserverInterface observer, String urlPath) {
        return new RegisterTask(firstName, lastName, alias, password, imageURL, new AuthenticateHandler(observer), getServerFacade(), urlPath);
    }

    LoginTask getLoginTask(String alias, String password, AuthenticateObserverInterface observer, String urlPath) {
        return new LoginTask(alias, password, new AuthenticateHandler(observer), getServerFacade(), urlPath);
    }

    GetUserTask getGetUserTask(AuthToken authToken, String alias, UserObserverInterface observer, String urlPath) {
        return new GetUserTask(authToken, alias, new UserHandler(observer), getServerFacade(), urlPath);
    }

    LogoutTask getLogoutTask(AuthToken authToken, SimpleObserverInterface observer, String urlPath) {
        return new LogoutTask(authToken, new SimpleHandler(observer), getServerFacade(), urlPath);
    }
}

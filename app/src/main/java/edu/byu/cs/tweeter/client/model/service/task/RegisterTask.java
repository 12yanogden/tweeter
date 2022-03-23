package edu.byu.cs.tweeter.client.model.service.task;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.AuthenticateResponse;

/**
 * Background task that creates a new user account and logs in the new user (i.e., starts a session).
 */
public class RegisterTask extends AuthenticateTask {
    private static final String LOG_TAG = "RegisterTask";

    /**
     * The user's first name.
     */
    private String firstName;
    /**
     * The user's last name.
     */
    private String lastName;

    /**
     * The base-64 encoded bytes of the user's profile image.
     */
    private String image;

    public RegisterTask(String firstName, String lastName, String alias, String password,
                        String image, Handler messageHandler, ServerFacade facade, String urlPath) {
        super(alias, password, messageHandler, facade, urlPath);

        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

    @Override
    protected AuthenticateResponse authenticate(String alias, String password, ServerFacade facade, String urlPath) throws IOException, TweeterRemoteException {
        RegisterRequest request = new RegisterRequest(firstName, lastName, alias, password, image);

        return getServerFacade().request(request, urlPath, AuthenticateResponse.class);
    }
}

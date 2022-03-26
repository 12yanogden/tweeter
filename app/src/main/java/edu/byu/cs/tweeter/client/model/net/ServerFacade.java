package edu.byu.cs.tweeter.client.model.net;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.AuthenticateRequest;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.request.Request;
import edu.byu.cs.tweeter.model.net.response.AuthenticateResponse;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;
import edu.byu.cs.tweeter.model.net.response.PagedStatusResponse;
import edu.byu.cs.tweeter.model.net.response.PagedUserResponse;
import edu.byu.cs.tweeter.model.net.response.Response;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {
    private static final String SERVER_URL = "https://y06riuh7sf.execute-api.us-west-2.amazonaws.com/prd";

    private final ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);

    public <Q extends Request, R extends Response> R request(Q request, String urlPath, Class<R> clazz) throws IOException, TweeterRemoteException {
        R response = clientCommunicator.doPost(urlPath, request, null, clazz);

        System.out.println("isSuccess: " + response.isSuccess());

        if (!response.isSuccess()) {
            throw new RuntimeException(response.getMessage());
        }

        return response;
    }
}
package edu.byu.cs.tweeter.client.model.net;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.Request;
import edu.byu.cs.tweeter.model.net.response.Response;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

    // TODO: Set this to the invoke URL of your API. Find it by going to your API in AWS, clicking
    //  on stages in the right-side menu, and clicking on the stage you deployed your API to.
    private static final String SERVER_URL = "https://y06riuh7sf.execute-api.us-west-2.amazonaws.com/prd";

    private final ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);

    public <R extends Response> R request(Request request, String urlPath, R response) throws IOException, TweeterRemoteException {
        response = (R) clientCommunicator.doPost(urlPath, request, null, response.getClass());

        if (!response.isSuccess()) {
            throw new RuntimeException(response.getMessage());
        }

        return response;
    }
}
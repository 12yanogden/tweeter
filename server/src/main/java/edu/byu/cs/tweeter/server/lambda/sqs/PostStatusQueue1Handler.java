package edu.byu.cs.tweeter.server.lambda.sqs;

import com.google.gson.Gson;

import java.util.List;

import edu.byu.cs.tweeter.model.net.request.PostStatusQueue2Request;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;

public class PostStatusQueue1Handler extends FollowServiceHandler {
    @Override
    protected void processJson(String json) {
        PostStatusRequest request = new Gson().fromJson(json, PostStatusRequest.class);

        getService().toPostStatusQueue2(request);
    }
}

package edu.byu.cs.tweeter.server.lambda.sqs;

import com.google.gson.Gson;

import edu.byu.cs.tweeter.model.net.request.PostStatusQueue2Request;

public class PostStatusQueue2Handler extends StatusServiceHandler {
    @Override
    protected void processJson(String json) {
        PostStatusQueue2Request request = new Gson().fromJson(json, PostStatusQueue2Request.class);

        getService().postStatusToFeeds(request);
    }
}

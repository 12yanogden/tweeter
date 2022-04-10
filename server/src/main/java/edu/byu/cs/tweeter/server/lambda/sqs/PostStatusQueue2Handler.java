package edu.byu.cs.tweeter.server.lambda.sqs;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.google.gson.Gson;

import edu.byu.cs.tweeter.model.net.request.PostStatusQueue2Request;

public class PostStatusQueue2Handler extends StatusServiceHandler implements RequestHandler<SQSEvent, Void> {
    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            PostStatusQueue2Request request = new Gson().fromJson(msg.getBody(), PostStatusQueue2Request.class);

            getService().postStatusToFeeds(request);
        }

        return null;
    }
}

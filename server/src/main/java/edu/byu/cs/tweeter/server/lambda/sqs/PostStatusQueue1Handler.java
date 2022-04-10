package edu.byu.cs.tweeter.server.lambda.sqs;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.google.gson.Gson;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;

public class PostStatusQueue1Handler extends FollowServiceHandler implements RequestHandler<SQSEvent, Void> {
    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            PostStatusRequest request = new Gson().fromJson(msg.getBody(), PostStatusRequest.class);

            getService().toPostStatusQueue2(request);
        }

        return null;
    }
}

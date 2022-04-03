package edu.byu.cs.tweeter.server.lambda.sqs;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.server.dao.aws.AWSDAOFactory;
import edu.byu.cs.tweeter.server.lambda.FactoryHandler;
import edu.byu.cs.tweeter.server.lambda.ServiceHandler;
import edu.byu.cs.tweeter.server.service.StatusService;

public abstract class SQSHandler extends ServiceHandler implements RequestHandler<SQSEvent, Void> {
    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            processJson(msg.getBody());
        }

        return null;
    }

    protected abstract void processJson(String json);
}

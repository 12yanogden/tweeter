package edu.byu.cs.tweeter.server.lambda.dynamoDB.status;

import edu.byu.cs.tweeter.server.lambda.dynamoDB.DynamoDBHandler;
import edu.byu.cs.tweeter.server.service.StatusService;

public class StatusServiceHandler extends DynamoDBHandler {
    protected StatusService getService() {
        return new StatusService(getFactory());
    }
}

package edu.byu.cs.tweeter.server.lambda.sqs;

import edu.byu.cs.tweeter.server.lambda.ServiceHandler;
import edu.byu.cs.tweeter.server.service.StatusService;

public abstract class StatusServiceHandler extends ServiceHandler {
    protected StatusService getService() {
        return new StatusService(getFactory());
    }
}

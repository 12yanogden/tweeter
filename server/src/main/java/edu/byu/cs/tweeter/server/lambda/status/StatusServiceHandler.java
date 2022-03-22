package edu.byu.cs.tweeter.server.lambda.status;

import edu.byu.cs.tweeter.server.lambda.ValidationHandler;
import edu.byu.cs.tweeter.server.service.StatusService;

public class StatusServiceHandler extends ValidationHandler {
    protected StatusService getService() {
        return new StatusService(getFactory());
    }
}

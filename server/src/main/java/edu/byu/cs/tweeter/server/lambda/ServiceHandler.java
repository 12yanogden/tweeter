package edu.byu.cs.tweeter.server.lambda;

import edu.byu.cs.tweeter.server.service.FactoryService;

public abstract class ServiceHandler extends FactoryHandler {
    protected abstract FactoryService getService();
}

package edu.byu.cs.tweeter.server.lambda.dynamoDB;

import edu.byu.cs.tweeter.server.service.FactoryService;

public abstract class ServiceHandler extends DynamoDBHandler {
    protected abstract FactoryService getService();
}

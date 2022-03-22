package edu.byu.cs.tweeter.server.lambda;

import edu.byu.cs.tweeter.server.dao.dynamoDB.DynamoDBDAOFactory;

public abstract class FactoryHandler {
    private DynamoDBDAOFactory factory;

    public FactoryHandler() {
        this.factory = getFactory();
    }

    protected DynamoDBDAOFactory getFactory() {
        if (factory == null) {
            factory = new DynamoDBDAOFactory();
        }

        return factory;
    }
}

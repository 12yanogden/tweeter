package edu.byu.cs.tweeter.server.lambda;

import edu.byu.cs.tweeter.server.dao.aws.AWSDAOFactory;

public abstract class FactoryHandler {
    private AWSDAOFactory factory;

    public FactoryHandler() {
        this.factory = getFactory();
    }

    protected AWSDAOFactory getFactory() {
        if (factory == null) {
            factory = new AWSDAOFactory();
        }

        return factory;
    }
}

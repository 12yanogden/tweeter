package edu.byu.cs.tweeter.server.lambda.dynamoDB;

import edu.byu.cs.tweeter.server.dao.dynamoDB.DynamoDBDAOFactory;
import edu.byu.cs.tweeter.server.lambda.FactoryHandler;

public abstract class DynamoDBHandler extends FactoryHandler<DynamoDBDAOFactory> {
    public DynamoDBHandler() {
        super(new DynamoDBDAOFactory());
    }

    @Override
    protected DynamoDBDAOFactory getFactory() {
        return factory;
    }
}

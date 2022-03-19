package edu.byu.cs.tweeter.server.lambda;

import edu.byu.cs.tweeter.server.dao.DAOFactory;

public abstract class FactoryHandler<F extends DAOFactory> {
    protected F factory;

    public FactoryHandler(F factory) {
        this.factory = factory;
    }

    protected abstract F getFactory();
}

package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.DAOFactory;

public abstract class FactoryService {
    protected DAOFactory factory;

    public FactoryService(DAOFactory factory) {
        this.factory = factory;
    }

    public DAOFactory getFactory() {
        return factory;
    }
}

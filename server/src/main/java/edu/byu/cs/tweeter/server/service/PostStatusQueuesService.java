package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.QueueDAO;

public class PostStatusQueuesService extends FactoryService {
    private final QueueDAO postStatusQueue1DAO;
    private final QueueDAO postStatusQueue2DAO;

    public PostStatusQueuesService(DAOFactory factory) {
        super(factory);

        postStatusQueue1DAO = factory.makePostStatusQueue1DAO();
        postStatusQueue2DAO = factory.makePostStatusQueue2DAO();
    }

    public QueueDAO getPostStatusQueue1DAO() {
        return postStatusQueue1DAO;
    }

    public QueueDAO getPostStatusQueue2DAO() {
        return postStatusQueue2DAO;
    }
}

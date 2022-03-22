package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.PagedDAO;
import edu.byu.cs.tweeter.server.dao.dynamoDB.DynamoDBPagedDAO;

public abstract class PagedService<T> extends FactoryService {
    public PagedService(DAOFactory factory) {
        super(factory);
    }

    public PagedResponse<T> getPagedItems(PagedRequest request) {
        return getDAO().getPagedItems(request);
    }

    protected abstract PagedDAO<T> getDAO();


}

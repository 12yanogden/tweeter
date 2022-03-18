package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;
import edu.byu.cs.tweeter.server.dao.dynamoDB.DynamoDBPagedDAO;

public abstract class PagedService<T> {
    public PagedResponse<T> getPagedItems(PagedRequest request) {
        if(request.getTargetUserAlias() == null) {
            throw new RuntimeException("[BadRequest] Request needs to have a targetUser alias");

        } else if(request.getLimit() <= 0) {
            throw new RuntimeException("[BadRequest] Request needs to have a positive limit");
        }

        return getDAO().getPagedItems(request);
    }

    protected abstract <D extends DynamoDBPagedDAO<T>> D getDAO();
}

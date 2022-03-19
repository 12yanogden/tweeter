package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;

public interface PagedDAO<T> {
    PagedResponse<T> getPagedItems(PagedRequest request);
}

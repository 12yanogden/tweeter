package edu.byu.cs.tweeter.model.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class PagedStatusResponse extends PagedResponse<Status> {
    public PagedStatusResponse(List<Status> items, boolean hasMorePages) {
        super(items, hasMorePages);
    }

    public PagedStatusResponse(String message) {
        super(message);
    }
}

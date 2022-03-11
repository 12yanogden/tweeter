package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;
import edu.byu.cs.tweeter.server.service.StatusService;

public class PagedStatusHandler implements RequestHandler<PagedRequest, PagedResponse<Status>> {
    @Override
    public PagedResponse<Status> handleRequest(PagedRequest request, Context context) {
        StatusService service = new StatusService();

        return service.getPagedItems(request);
    }
}

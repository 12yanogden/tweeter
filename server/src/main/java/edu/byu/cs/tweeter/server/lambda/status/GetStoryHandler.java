package edu.byu.cs.tweeter.server.lambda.status;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;

public class GetStoryHandler extends StatusServiceHandler implements RequestHandler<PagedRequest, PagedResponse<Status>> {
    @Override
    public PagedResponse<Status> handleRequest(PagedRequest request, Context context) {
        validateAlias("target user alias", request.getTargetUserAlias());
        validateNotNull("limit", request.getLimit());
        validatePositive("limit", request.getLimit());

        PagedResponse<Status> response = getService().getStory(request);

        System.out.println("isSuccess: " + response.isSuccess());
        for (Status item: response.getItems()) {
            System.out.println(item);
        }
        System.out.println("hasMorePages: " + response.getHasMorePages());

        return response;
    }
}

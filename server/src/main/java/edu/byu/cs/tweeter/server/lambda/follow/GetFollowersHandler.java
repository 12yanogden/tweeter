package edu.byu.cs.tweeter.server.lambda.follow;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;

public class GetFollowersHandler extends FollowServiceHandler implements RequestHandler<PagedRequest, PagedResponse<User>> {
    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the followees.
     */
    @Override
    public PagedResponse<User> handleRequest(PagedRequest request, Context context) {
        validateAlias("target user alias", request.getTargetUserAlias());
        validateNotNull("limit", request.getLimit());
        validatePositive("limit", request.getLimit());

        PagedResponse<User> response = getService().getFollowers(request);

        System.out.println("isSuccess: " + response.isSuccess());
        for (User item: response.getItems()) {
            System.out.println(item);
        }
        System.out.println("hasMorePages: " + response.getHasMorePages());

        return response;
    }
}

package edu.byu.cs.tweeter.client.model.service;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PagedHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.SimpleHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PagedObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService extends ModelService {
    public void getFeed(AuthToken authToken, User user, int pageSize, Status lastStatus, PagedObserver<Status> observer) { // TODO: Is there duplication here?
        GetFeedTask getFeedTask = new GetFeedTask(authToken, user, pageSize, lastStatus, new PagedHandler<>(observer));

        execute(getFeedTask);
    }

    public void getStory(AuthToken authToken, User user, int pageSize, Status lastStatus, PagedObserver<Status> observer) {
        GetStoryTask getStoryTask = new GetStoryTask(authToken, user, pageSize, lastStatus, new PagedHandler<>(observer));

        execute(getStoryTask);
    }

    public void postStatus(String post, User user, String dateTime, List<String> urls, List<String> mentions, AuthToken authToken, SimpleObserver observer) {
        Status newStatus = new Status(post, user, dateTime, urls, mentions);

        PostStatusTask statusTask = new PostStatusTask(authToken, newStatus, new SimpleHandler(observer));

        execute(statusTask);
    }
}

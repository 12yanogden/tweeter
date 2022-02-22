package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedStatusTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PagedHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.SimpleHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PagedObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService extends ModelService {
    public void getFeed(AuthToken authToken, User user, int pageSize, Status lastStatus, PagedObserver<Status> observer) {
        PagedStatusTask pagedStatusTask = new PagedStatusTask(authToken, user, pageSize, lastStatus, new PagedHandler<>(observer));

        execute(pagedStatusTask);
    }

    public void getStory(AuthToken authToken, User user, int pageSize, Status lastStatus, PagedObserver<Status> observer) {
        PagedStatusTask getStoryTask = new PagedStatusTask(authToken, user, pageSize, lastStatus, new PagedHandler<>(observer));

        execute(getStoryTask);
    }

    public void postStatus(Status status, AuthToken authToken, SimpleObserver observer) {
        PostStatusTask statusTask = new PostStatusTask(authToken, status, new SimpleHandler(observer));

        execute(statusTask);
    }
}

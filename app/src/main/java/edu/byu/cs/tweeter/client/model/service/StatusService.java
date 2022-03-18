package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.task.PagedStatusTask;
import edu.byu.cs.tweeter.client.model.service.task.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.handler.PagedHandler;
import edu.byu.cs.tweeter.client.model.service.handler.SimpleHandler;
import edu.byu.cs.tweeter.client.model.service.observerInterface.PagedObserverInterface;
import edu.byu.cs.tweeter.client.model.service.observerInterface.SimpleObserverInterface;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService extends ModelService {
    public void getFeed(AuthToken authToken, User user, int pageSize, Status lastStatus, PagedObserverInterface<Status> observer) {
        execute(getPagedStatusTask(authToken, user, pageSize, lastStatus, observer, "/getfeed"));
    }

    public void getStory(AuthToken authToken, User user, int pageSize, Status lastStatus, PagedObserverInterface<Status> observer) {
        execute(getPagedStatusTask(authToken, user, pageSize, lastStatus, observer, "/getstory"));
    }

    public void postStatus(Status status, AuthToken authToken, SimpleObserverInterface observer) {
        execute(getPostStatusTask(status, authToken, observer, "/poststatus"));
    }

    public PagedStatusTask getPagedStatusTask(AuthToken authToken, User user, int pageSize, Status lastStatus, PagedObserverInterface<Status> observer, String urlPath) {
        return new PagedStatusTask(authToken, user, pageSize, lastStatus, new PagedHandler<>(observer), getServerFacade(), urlPath);
    }

    public PostStatusTask getPostStatusTask(Status status, AuthToken authToken, SimpleObserverInterface observer, String urlPath) {
        return new PostStatusTask(authToken, status, new SimpleHandler(observer), getServerFacade(), urlPath);
    }
}

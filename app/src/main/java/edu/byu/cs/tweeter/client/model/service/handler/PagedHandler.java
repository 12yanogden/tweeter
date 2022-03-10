package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.task.PagedStatusTask;
import edu.byu.cs.tweeter.client.model.service.task.PagedTask;
import edu.byu.cs.tweeter.client.model.service.observerInterface.PagedObserverInterface;

public class PagedHandler<T> extends BackgroundTaskHandler<PagedObserverInterface<T>> {

    public PagedHandler(PagedObserverInterface<T> observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, PagedObserverInterface<T> observer) {
        List<T> items = (List<T>) data.getSerializable(PagedTask.ITEMS_KEY);
        boolean hasMorePages = data.getBoolean(PagedStatusTask.MORE_PAGES_KEY);

        observer.handleSuccess(items, hasMorePages);
    }
}

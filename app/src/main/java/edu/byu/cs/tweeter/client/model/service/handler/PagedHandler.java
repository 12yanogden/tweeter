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
        List<T> items = (List<T>) data.getSerializable(PagedTask.ITEMS_KEY); // TODO: List is of LinkedTreeMaps

        System.out.println("+---------------------------------------------------------------------------------------+");
        System.out.println("|                                                                                       |");
        System.out.println("|                                    Testing List<T>                                    |");
        System.out.println("|                                                                                       |");
        System.out.println("+---------------------------------------------------------------------------------------+");
        System.out.println("data: " + data);
        System.out.println("+---------------------------------------------------------------------------------------+");
        System.out.println("ITEMS_KEY: " + PagedTask.ITEMS_KEY);
        System.out.println("+---------------------------------------------------------------------------------------+");
        System.out.println("data.getSerializable(): " + data.getSerializable(PagedTask.ITEMS_KEY));
        System.out.println("+---------------------------------------------------------------------------------------+");
        System.out.println("items.get(0).getClass(): " + items.get(0).getClass());
        System.out.println("+---------------------------------------------------------------------------------------+");

        boolean hasMorePages = data.getBoolean(PagedStatusTask.MORE_PAGES_KEY);

        observer.handleSuccess(items, hasMorePages);
    }
}

package edu.byu.cs.tweeter.client.model.service.observerInterface;

import java.util.List;

public interface PagedObserverInterface<T> extends ServiceObserverInterface {
    void handleSuccess(List<T> items, boolean hasMorePages);
}

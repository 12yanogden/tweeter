package edu.byu.cs.tweeter.client.model.service.observerInterface;

public interface ServiceObserverInterface {
    void handleFailure(String message);
    void handleException(Exception exception);
}

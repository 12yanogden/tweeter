package edu.byu.cs.tweeter.client.model.service.observerInterface;

public interface CountObserverInterface extends ServiceObserverInterface {
    void handleSuccess(int count);
}

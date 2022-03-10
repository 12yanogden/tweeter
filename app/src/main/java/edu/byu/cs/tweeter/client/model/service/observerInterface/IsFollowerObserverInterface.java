package edu.byu.cs.tweeter.client.model.service.observerInterface;

public interface IsFollowerObserverInterface extends ServiceObserverInterface {
    void handleSuccess(boolean isFollower);
}

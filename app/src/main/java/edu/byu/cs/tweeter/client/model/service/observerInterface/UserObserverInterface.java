package edu.byu.cs.tweeter.client.model.service.observerInterface;

import edu.byu.cs.tweeter.model.domain.User;

public interface UserObserverInterface extends ServiceObserverInterface {
    void handleSuccess(User user);
}

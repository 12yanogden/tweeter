package edu.byu.cs.tweeter.client.model.service.observerInterface;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public interface AuthenticateObserverInterface extends ServiceObserverInterface {
    void handleSuccess(User user, AuthToken authToken);
}

package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.net.request.Request;

public interface QueueDAO {
    void sendToQueue(String requestJson);
}

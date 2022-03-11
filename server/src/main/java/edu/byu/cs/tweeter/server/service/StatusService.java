package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.dao.PagedDAO;
import edu.byu.cs.tweeter.server.dao.StatusDAO;

public class StatusService extends PagedService<Status> {
    public PagedDAO<Status> getDAO() {
        return new StatusDAO();
    }
}

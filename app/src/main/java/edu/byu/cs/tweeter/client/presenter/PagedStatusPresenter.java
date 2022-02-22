package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.model.domain.Status;

public abstract class PagedStatusPresenter extends PagedPresenter<Status> {
    protected StatusService statusService;

    public PagedStatusPresenter(PagedView<Status> view) {
        super(view);
    }

    protected StatusService getStatusService() {
        if (statusService == null) {
            statusService = new StatusService();
        }

        return statusService;
    }
}

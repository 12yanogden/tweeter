package edu.byu.cs.tweeter.client.presenter.observer;

import java.util.List;

import edu.byu.cs.tweeter.client.presenter.PagedPresenter;

public class PagedObserver<T> extends PresenterObserver implements edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PagedObserver<T> {
    protected final PagedPresenter<T> presenter;

    public PagedObserver(PagedPresenter<T> presenter, String description) {
        super(description);

        this.presenter = presenter;
    }
    
    @Override
    public void handleSuccess(List<T> items, boolean hasMorePages) {
        updatePageState();

        presenter.setLastItem((items.size() > 0) ? items.get(items.size() - 1) : null);

        presenter.setHasMorePages(hasMorePages);

        presenter.getView().addItems(items);
    }

    protected void updatePageState() {
        presenter.setLoading(false);
    }

    @Override
    protected PagedPresenter<T> getPresenter() {
        return presenter;
    }
}

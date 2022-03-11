package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;
import edu.byu.cs.tweeter.util.FakeData;

public abstract class PagedDAO<T> extends DAO {
    public PagedResponse<T> getPagedItems(PagedRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getLimit() > 0;
        assert request.getTargetUserAlias() != null;

        List<T> allItems = getAllItems();
        List<T> pagedItems = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allItems != null) {
                int allUsersIndex = getStartingIndex(request.getLastItemId(), allItems);

                for(int limitCounter = 0; allUsersIndex < allItems.size() && limitCounter < request.getLimit(); allUsersIndex++, limitCounter++) {
                    pagedItems.add(allItems.get(allUsersIndex));
                }

                hasMorePages = allUsersIndex < allItems.size();
            }
        }

        return new PagedResponse<>(pagedItems, hasMorePages);
    }

    protected abstract List<T> getAllItems();

    private int getStartingIndex(String lastItemId, List<T> allItems) {
        int index = 0;

        if(lastItemId != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allItems.size(); i++) {
                if(lastItemId.equals(getItemId(allItems.get(i)))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    index = i + 1;
                    break;
                }
            }
        }

        return index;
    }

    protected abstract String getItemId(T item);
}

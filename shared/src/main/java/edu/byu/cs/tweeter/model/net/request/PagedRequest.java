package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.util.Pair;

public class PagedRequest extends AuthenticatedRequest {
    private String targetUserAlias;
    private int limit;
    private Pair<String, String> lastItemId;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private PagedRequest() {
        super();
    }

    public PagedRequest(AuthToken authToken, String userAlias, int limit, Pair<String, String> lastItemId) {
        super(authToken);

        this.targetUserAlias = userAlias;
        this.limit = limit;
        this.lastItemId = lastItemId;
    }

    public String getTargetUserAlias() {
        return targetUserAlias;
    }

    public void setTargetUserAlias(String userAlias) {
        this.targetUserAlias = userAlias;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Pair<String, String> getLastItemId() {
        return lastItemId;
    }

    public void setLastItemId(Pair<String, String> lastItemId) {
        this.lastItemId = lastItemId;
    }
}

package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.util.Pair;

public class PagedRequest extends AuthenticatedRequest {
    private String targetUserAlias;
    private int limit;
    private String alias;
    private String dateTime;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private PagedRequest() {
        super();
    }

    public PagedRequest(AuthToken authToken, String userAlias, int limit, String alias, String dateTime) {
        super(authToken);

        this.targetUserAlias = userAlias;
        this.limit = limit;
        this.alias = alias;
        this.dateTime = dateTime;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}

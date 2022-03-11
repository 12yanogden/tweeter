package edu.byu.cs.tweeter.model.net.response;

import java.util.List;
import java.util.Objects;

/**
 * A response that can indicate whether there is more data available from the server.
 */
public class PagedResponse<T> extends Response {
    private List<T> items;
    private final boolean hasMorePages;

    public PagedResponse(List<T> items, boolean hasMorePages) {
        super(true);

        this.items = items;
        this.hasMorePages = hasMorePages;
    }

    public PagedResponse(String message) {
        super(false, message);

        this.hasMorePages = false;
    }

    /**
     * An indicator of whether more data is available from the server. A value of true indicates
     * that the result was limited by a maximum value in the request and an additional request
     * would return additional data.
     *
     * @return true if more data is available; otherwise, false.
     */
    public boolean getHasMorePages() {
        return hasMorePages;
    }

    public List<T> getItems() {
        System.out.println("+---------------------------------------------------------------------------------------+");
        System.out.println("|                                                                                       |");
        System.out.println("|                                     In response                                       |");
        System.out.println("|                                                                                       |");
        System.out.println("+---------------------------------------------------------------------------------------+");
        System.out.println("items.get(0).getClass(): " + items.get(0).getClass());
        System.out.println("+---------------------------------------------------------------------------------------+");

        return items;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItems());
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        PagedResponse<T> that = (PagedResponse<T>) param;

        return (Objects.equals(getItems(), that.getItems()) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }
}

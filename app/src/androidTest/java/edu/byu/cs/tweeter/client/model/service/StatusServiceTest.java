package edu.byu.cs.tweeter.client.model.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.byu.cs.tweeter.client.model.service.observerInterface.PagedObserverInterface;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusServiceTest {
    private StatusService statusServiceSpy;
    private AuthToken authToken;
    private User user;
    private PagedStatusObserver observer;
    private CountDownLatch countDownLatch;

    @Before
    public void setup() {
        statusServiceSpy = Mockito.spy(new StatusService());
        authToken = new AuthToken();
        user = new User("Allen", "Anderson", "@allen", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        observer = new PagedStatusObserver();

        resetCountDownLatch();
    }

    private void resetCountDownLatch() {
        countDownLatch = new CountDownLatch(1);
    }

    private void awaitCountDownLatch() throws InterruptedException {
        countDownLatch.await();
        resetCountDownLatch();
    }


    private class PagedStatusObserver implements PagedObserverInterface<Status> {
        private boolean success;
        private String message;
        private List<Status> statuses;
        private boolean hasMorePages;
        private Exception exception;

        @Override
        public void handleSuccess(List<Status> statuses, boolean hasMorePages) {
            this.success = true;
            this.message = null;
            this.statuses = statuses;
            this.hasMorePages = hasMorePages;
            this.exception = null;

            countDownLatch.countDown();
        }

        @Override
        public void handleFailure(String message) {
            this.success = false;
            this.message = message;
            this.statuses = null;
            this.hasMorePages = false;
            this.exception = null;

            countDownLatch.countDown();
        }

        @Override
        public void handleException(Exception exception) {
            this.success = false;
            this.message = null;
            this.statuses = null;
            this.hasMorePages = false;
            this.exception = exception;

            countDownLatch.countDown();
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public List<Status> getStatuses() {
            return statuses;
        }

        public boolean getHasMorePages() {
            return hasMorePages;
        }

        public Exception getException() {
            return exception;
        }
    }

    @Test
    public void testGetStory_getStory_returnList() throws InterruptedException {
        int pageSize = 3;

        statusServiceSpy.getStory(authToken, user, pageSize, null, observer);
        awaitCountDownLatch();

        Assert.assertTrue(observer.isSuccess());
        Assert.assertNull(observer.getMessage());
        Assert.assertEquals(pageSize, observer.getStatuses().size());
        Assert.assertTrue(observer.getHasMorePages());
        Assert.assertNull(observer.getException());
    }
}

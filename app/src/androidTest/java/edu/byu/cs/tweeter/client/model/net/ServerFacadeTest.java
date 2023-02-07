package edu.byu.cs.tweeter.client.model.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import edu.byu.cs.tweeter.client.model.service.observerInterface.SimpleObserverInterface;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.client.presenter.observer.MainObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.AuthenticateRequest;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.AuthenticateResponse;
import edu.byu.cs.tweeter.model.net.response.PagedStatusResponse;

public class ServerFacadeTest {
    private User allen;
    private AuthToken authToken;
    private String postMsg;
    private String password;
    private CountDownLatch countDownLatch;
    private ServerFacade serverFacade;
    private MainPresenter.MainView mockMainView;
    private MainPresenter mainPresenter;
    private PagedRequest getStoryRequest;

    @Before
    public void setup() {
        allen = makeAllen();
        password = "pass";
        postMsg = makeUniquePost();

        serverFacade = new ServerFacade();

        mockMainView = Mockito.mock(MainPresenter.MainView.class);
        mainPresenter = spy(new MainPresenter(mockMainView));

        resetCountDownLatch();
    }

    @Test
    public void testPostStatus_storyHasPostedStatus() {
        /*─────────────────────────────────────────────────────────────────────────────────────*\
        │                                                                                       │
        │                                         Login                                         │
        │                                                                                       │
        \*─────────────────────────────────────────────────────────────────────────────────────*/
        AuthenticateRequest request = new AuthenticateRequest(allen.getAlias(), password);
        AuthenticateResponse response = new AuthenticateResponse(null, null);
        String loginPath = "/login";

        try {
            response = serverFacade.request(request, loginPath, AuthenticateResponse.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        assertNotNull(response.getAuthToken());

        authToken = response.getAuthToken();

        when(mainPresenter.getCurrentAuthToken()).thenReturn(authToken);
        when(mainPresenter.getCurrentUser()).thenReturn(allen);
        when(mainPresenter.getPostStatusObserver()).thenReturn(new PostStatusObserver(mainPresenter, "post status"));

        /*─────────────────────────────────────────────────────────────────────────────────────*\
        │                                                                                       │
        │                                      Post Status                                      │
        │                                                                                       │
        \*─────────────────────────────────────────────────────────────────────────────────────*/
        String LOG_TAG = "testPostStatus";
        String postingMsg = "Posting status...";
        String successMsg = "Successfully Posted!";

        try {
            mainPresenter.postStatus(postMsg, LOG_TAG);
            awaitCountDownLatch();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        Mockito.verify(mockMainView).displayToast(eq(postingMsg));
        Mockito.verify(mockMainView).displayPostStatusSuccess(eq(successMsg));

        /*─────────────────────────────────────────────────────────────────────────────────────*\
        │                                                                                       │
        │                                       Get Story                                       │
        │                                                                                       │
        \*─────────────────────────────────────────────────────────────────────────────────────*/
        getStoryRequest = new PagedRequest(authToken, allen.getAlias(), 10, null, null);
        PagedStatusResponse getStoryResponse = new PagedStatusResponse("placeholder");
        String getStoryPath = "/getstory";

        try {
            getStoryResponse = serverFacade.request(getStoryRequest, getStoryPath, PagedStatusResponse.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        assertNotNull(getStoryResponse);
        assertTrue(getStoryResponse.isSuccess());

        Status mostRecentItem = getStoryResponse.getItems().get(0);

        assertTrue(allen.equals(mostRecentItem.getUser()));
        assertNotNull(mostRecentItem.getDate());
        assertTrue(postMsg.equals(mostRecentItem.getPost()));
        assertNotNull(mostRecentItem.getMentions());
        assertTrue(mostRecentItem.getMentions().isEmpty());
        assertTrue(mostRecentItem.getUrls().isEmpty());
    }

    private String makeUniquePost() {
        return UUID.randomUUID().toString();
    }

    private User makeAllen() {
        return new User("Allen",
                "Anderson",
                "@allen",
                "https://ogden9-tweeter.s3.us-west-2.amazonaws.com/donald_duck.png");
    }

    private void resetCountDownLatch() {
        countDownLatch = new CountDownLatch(1);
    }

    private void awaitCountDownLatch() throws InterruptedException {
        countDownLatch.await();
        resetCountDownLatch();
    }

    /**
     * A {@link MainObserver} implementation that can be used to get the values
     * eventually returned by an asynchronous call on the {@link PostStatusObserver}. Counts down
     * on the countDownLatch so tests can wait for the background thread to call a method on the
     * observer.
     */
    private class PostStatusObserver extends MainObserver implements SimpleObserverInterface {
        private boolean success;
        private String message;
        private Exception exception;

        public PostStatusObserver(MainPresenter presenter, String description) {
            super(presenter, description);
        }

        @Override
        public void handleSuccess() {
            this.success = true;
            this.message = null;
            this.exception = null;

            getPresenter().getView().displayPostStatusSuccess("Successfully Posted!");

            countDownLatch.countDown();
        }

        @Override
        public void handleFailure(String message) {
            this.success = false;
            this.message = message;
            this.exception = null;

            countDownLatch.countDown();
        }

        @Override
        public void handleException(Exception exception) {
            this.success = false;
            this.message = null;
            this.exception = exception;

            countDownLatch.countDown();
        }
    }
}

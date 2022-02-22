package edu.byu.cs.tweeter.client.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.presenter.observer.PostStatusObserver;
import edu.byu.cs.tweeter.model.domain.Status;

public class MainPresenterUnitTest {
    private MainPresenter.MainView mockMainView;
    private StatusService mockStatusService;
    private Cache mockCache;
    private PostStatusObserver spyObserver;

    private MainPresenter spyMainPresenter;

    private String postMsg;

    @Before
    public void setup() {
        // Create mocks
        mockMainView = Mockito.mock(MainPresenter.MainView.class);
        mockStatusService = Mockito.mock(StatusService.class);
        mockCache = Mockito.mock(Cache.class);

        // Create spies
        spyMainPresenter = Mockito.spy(new MainPresenter(mockMainView));
        spyObserver = Mockito.spy(new PostStatusObserver(spyMainPresenter, "post status"));
        Mockito.when(spyMainPresenter.getStatusService()).thenReturn(mockStatusService);
        Mockito.when(spyMainPresenter.getPostStatusObserver()).thenReturn(spyObserver);

        // Set cache
        Cache.setInstance(mockCache);

        // Create postMsg
        postMsg = "testPost";
    }

    @Test
    public void testPostStatus_handleSuccess() {
        try {
            Status status = new Status(postMsg,
                    mockCache.getCurrUser(),
                    spyMainPresenter.getFormattedDateTime(),
                    spyMainPresenter.parseURLs(postMsg),
                    spyMainPresenter.parseMentions(postMsg));


            PostStatusSuccessAnswer answer = new PostStatusSuccessAnswer();

            mockPostStatus(answer);

            Mockito.verify(mockStatusService).postStatus(status, mockCache.getCurrUserAuthToken(), spyMainPresenter.getPostStatusObserver());
            Mockito.verify(mockMainView).displayPostStatusSuccess("Successfully Posted!");
        } catch (Exception e) {
            // Empty
        }
    }

    @Test
    public void testPostStatus_handleFailure() {
        String msg = "u done did caused a failure";
        PostStatusFailureAnswer answer = new PostStatusFailureAnswer(msg);

        mockPostStatus(answer);

        Mockito.verify(mockMainView).displayToast("Failed to post status: " + msg);
    }

    @Test
    public void testPostStatus_handleException() {
        String msg = "u done did caused an exception";
        PostStatusExceptionAnswer answer = new PostStatusExceptionAnswer(msg);

        mockPostStatus(answer);

        Mockito.verify(mockMainView).displayToast("Failed to post status because of exception: " + msg);
    }

    private abstract class PostStatusAnswer implements Answer<Void> {
        @Override
        public Void answer(InvocationOnMock invocation) throws Throwable {
            PostStatusObserver observer = invocation.getArgument(2, PostStatusObserver.class);

            handleCustom(observer);

            return null;
        }

        protected abstract void handleCustom(PostStatusObserver observer);
    }

    private class PostStatusSuccessAnswer extends PostStatusAnswer {
        @Override
        protected void handleCustom(PostStatusObserver observer) {
            observer.handleSuccess();
        }
    }

    private abstract class PostStatusMessageAnswer extends PostStatusAnswer {
        protected String msg;

        public PostStatusMessageAnswer(String msg) {
            this.msg = msg;
        }
    }

    private class PostStatusFailureAnswer extends PostStatusMessageAnswer {
        public PostStatusFailureAnswer(String msg) {
            super(msg);
        }

        @Override
        protected void handleCustom(PostStatusObserver observer) {
            observer.handleFailure(msg);
        }
    }

    private class PostStatusExceptionAnswer extends PostStatusMessageAnswer {
        public PostStatusExceptionAnswer(String msg) {
            super(msg);
        }

        @Override
        protected void handleCustom(PostStatusObserver observer) {
            observer.handleException(new Exception(msg));
        }
    }

    private <A extends PostStatusAnswer> void mockPostStatus(A answer) {
        Mockito.doAnswer(answer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any(), Mockito.any());
        spyMainPresenter.postStatus("testPost", "MainActivity");
    }
}

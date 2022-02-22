package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService {
    public interface GetFeedObserver {
        void handleSuccess(List<Status> statuses, boolean hasMorePages);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public void getFeed(AuthToken authToken, User user, int pageSize, Status lastStatus, GetFeedObserver getFeedObserver) {
        GetFeedTask getFeedTask = new GetFeedTask(authToken,
                user, pageSize, lastStatus, new GetFeedHandler(getFeedObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFeedTask);
    }

    /**
     * Message handler (i.e., observer) for GetFeedTask.
     */
    private class GetFeedHandler extends Handler {
        private GetFeedObserver getFeedObserver;

        public GetFeedHandler(GetFeedObserver observer) {
            this.getFeedObserver = observer;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(GetFeedTask.SUCCESS_KEY);

            if (success) {
                List<Status> statuses = (List<Status>) msg.getData().getSerializable(GetFeedTask.STATUSES_KEY);
                boolean hasMorePages = msg.getData().getBoolean(GetFeedTask.MORE_PAGES_KEY);

                getFeedObserver.handleSuccess(statuses, hasMorePages);

            } else if (msg.getData().containsKey(GetFeedTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetFeedTask.MESSAGE_KEY);
                getFeedObserver.handleFailure(message);

            } else if (msg.getData().containsKey(GetFeedTask.EXCEPTION_KEY)) {
                Exception exception = (Exception) msg.getData().getSerializable(GetFeedTask.EXCEPTION_KEY);
                getFeedObserver.handleException(exception);
            }
        }
    }

    public interface GetStoryObserver {
        void handleSuccess(List<Status> statuses, boolean hasMorePages);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public void getStory(AuthToken authToken, User user, int pageSize, Status lastStatus, GetStoryObserver getStoryObserver) {
        GetStoryTask getStoryTask = new GetStoryTask(authToken,
                user, pageSize, lastStatus, new GetStoryHandler(getStoryObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getStoryTask);
    }

    /**
     * Message handler (i.e., observer) for GetStoryTask.
     */
    private class GetStoryHandler extends Handler {
        private GetStoryObserver getStoryObserver;

        public GetStoryHandler(GetStoryObserver observer) {
            this.getStoryObserver = observer;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(GetStoryTask.SUCCESS_KEY);

            if (success) {
                List<Status> statuses = (List<Status>) msg.getData().getSerializable(GetStoryTask.STATUSES_KEY);
                boolean hasMorePages = msg.getData().getBoolean(GetStoryTask.MORE_PAGES_KEY);

                getStoryObserver.handleSuccess(statuses, hasMorePages);

            } else if (msg.getData().containsKey(GetStoryTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetStoryTask.MESSAGE_KEY);
                getStoryObserver.handleFailure(message);

            } else if (msg.getData().containsKey(GetStoryTask.EXCEPTION_KEY)) {
                Exception exception = (Exception) msg.getData().getSerializable(GetStoryTask.EXCEPTION_KEY);
                getStoryObserver.handleException(exception);
            }
        }
    }

    public interface PostStatusObserver {
        void handleSuccess();
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public void postStatus(String post, User user, String dateTime, List<String> urls, List<String> mentions, AuthToken authToken, MainPresenter.PostStatusObserver postStatusObserver) {
        Status newStatus = new Status(post, user, dateTime, urls, mentions);

        PostStatusTask statusTask = new PostStatusTask(authToken,
                newStatus, new PostStatusHandler(postStatusObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(statusTask);
    }

    private class PostStatusHandler extends Handler {
        private PostStatusObserver postStatusObserver;

        public PostStatusHandler(PostStatusObserver postStatusObserver) {
            this.postStatusObserver = postStatusObserver;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(PostStatusTask.SUCCESS_KEY);

            if (success) {
                postStatusObserver.handleSuccess();

            } else if (msg.getData().containsKey(PostStatusTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(PostStatusTask.MESSAGE_KEY);

                postStatusObserver.handleFailure(message);

            } else if (msg.getData().containsKey(PostStatusTask.EXCEPTION_KEY)) {
                Exception exception = (Exception) msg.getData().getSerializable(PostStatusTask.EXCEPTION_KEY);

                postStatusObserver.handleException(exception);
            }
        }
    }
}

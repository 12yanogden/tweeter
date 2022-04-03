package edu.byu.cs.tweeter.server.dao.aws;

import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.BucketDAO;
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.dao.aws.dynamoDB.DynamoDBAuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.aws.dynamoDB.DynamoDBFeedDAO;
import edu.byu.cs.tweeter.server.dao.aws.dynamoDB.DynamoDBFollowDAO;
import edu.byu.cs.tweeter.server.dao.aws.dynamoDB.DynamoDBStoryDAO;
import edu.byu.cs.tweeter.server.dao.aws.dynamoDB.DynamoDBUserDAO;
import edu.byu.cs.tweeter.server.dao.aws.s3.S3BucketDAO;
import edu.byu.cs.tweeter.server.dao.aws.sqs.PostStatusQueue1DAO;
import edu.byu.cs.tweeter.server.dao.aws.sqs.PostStatusQueue2DAO;

public class AWSDAOFactory implements DAOFactory {
    private final String region;

    public AWSDAOFactory() {
        this.region = "us-west-2";
    }

    @Override
    public AuthTokenDAO makeAuthTokenDAO() {
        return new DynamoDBAuthTokenDAO(getRegion());
    }

    @Override
    public BucketDAO makeBucketDAO() {
        return new S3BucketDAO(getRegion());
    }

    @Override
    public FollowDAO makeFollowDAO() {
        return new DynamoDBFollowDAO(getRegion());
    }

    @Override
    public StoryDAO makeStoryDAO() {
        return new DynamoDBStoryDAO(getRegion());
    }

    @Override
    public FeedDAO makeFeedDAO() {
        return new DynamoDBFeedDAO(getRegion());
    }

    @Override
    public PostStatusQueue1DAO makePostStatusQueue1DAO() {
        return new PostStatusQueue1DAO(getRegion());
    }

    @Override
    public PostStatusQueue2DAO makePostStatusQueue2DAO() {
        return new PostStatusQueue2DAO(getRegion());
    }

    @Override
    public UserDAO makeUserDAO() {
        return new DynamoDBUserDAO(getRegion());
    }

    public String getRegion() {
        return region;
    }
}

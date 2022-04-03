package edu.byu.cs.tweeter.server.dao.aws.sqs;

public class PostStatusQueue1DAO extends PostStatusQueueDAO {
    public PostStatusQueue1DAO(String region) {
        super(region);

        this.queueName = "PostStatusQueue1";
    }
}

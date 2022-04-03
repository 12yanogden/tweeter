package edu.byu.cs.tweeter.server.dao.aws.sqs;

public class PostStatusQueue2DAO extends PostStatusQueueDAO {

    public PostStatusQueue2DAO(String region) {
        super(region);

        this.queueName = "PostStatusQueue2";
    }
}

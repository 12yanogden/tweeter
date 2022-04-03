package edu.byu.cs.tweeter.server.dao.aws.sqs;

public class PostStatusQueueDAO extends SQSDAO {
    private String queueURL;

    public PostStatusQueueDAO(String region) {
        super(region);

        this.queueURL = getSqsURL() + "/" + getQueueName();
    }
}

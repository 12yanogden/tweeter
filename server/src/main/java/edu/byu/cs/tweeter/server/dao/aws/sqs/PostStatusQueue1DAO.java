package edu.byu.cs.tweeter.server.dao.aws.sqs;

public class PostStatusQueue1DAO extends SQSDAO {
    public PostStatusQueue1DAO(String region) {
        super(region, "PostStatusQueue1");
    }
}

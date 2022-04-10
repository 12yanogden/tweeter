package edu.byu.cs.tweeter.server.dao.aws.sqs;

public class PostStatusQueue2DAO extends SQSDAO {
    public PostStatusQueue2DAO(String region) {
        super(region, "PostStatusQueue2");
    }
}

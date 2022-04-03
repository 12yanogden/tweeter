package edu.byu.cs.tweeter.server.dao;

public interface BucketDAO {
    void putImage(String keyName, String image);
    String getBucketURL();
}

package edu.byu.cs.tweeter.server.dao.dynamoDB;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

public class S3Facade {
    private AmazonS3 s3;

    public S3Facade(String region) {
        s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .build();
    }

    public String putStreamInBucket(String bucketName, String keyName, InputStream inputStream, ObjectMetadata metadata) {
        try {
            s3.putObject(bucketName, keyName, inputStream, metadata);

        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            e.printStackTrace();
        }

        return s3.getUrl(bucketName, keyName).toString();
    }
}

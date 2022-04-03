package edu.byu.cs.tweeter.server.dao.aws.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

import javax.print.event.PrintJobEvent;

public class S3Facade {
    private final AmazonS3 s3;
    private final String region;

    public S3Facade(String region) {
        this.region = region;
        this.s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion(getRegion())
                .build();
    }

    public void putObject(PutObjectRequest request) {
        try {
            getS3().putObject(request);

        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            e.printStackTrace();
        }
    }

    public String getS3URL() {
        return "https://s3." + getRegion() + ".amazonaws.com/";
    }

    public AmazonS3 getS3() {
        return s3;
    }

    public String getRegion() {
        return region;
    }
}

package edu.byu.cs.tweeter.server.dao.dynamoDB;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

public class S3Facade {
    private AmazonS3 s3;

    public S3Facade(String region) {
        s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .build();
    }

    public String putImageInBucket(String bucketName, String keyName, String image) {
        try {
            PutObjectRequest request = makeRequest(bucketName, keyName, image);

            s3.putObject(request);

        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            e.printStackTrace();
        }

        return s3.getUrl(bucketName, keyName).toString();
    }

    private PutObjectRequest makeRequest(String bucketName, String keyName, String image) {
        return new PutObjectRequest(bucketName, keyName, imageToStream(image), makeMetadata("image/png")).withCannedAcl(CannedAccessControlList.PublicRead);
    }

    private InputStream imageToStream(String image) {
        byte[] imageBytes = Base64.getDecoder().decode(image);

        return new ByteArrayInputStream(imageBytes);
    }

    private ObjectMetadata makeMetadata(String contentType) {
        ObjectMetadata metadata = new ObjectMetadata();

//        metadata.setContentType(contentType);

        return metadata;
    }
}

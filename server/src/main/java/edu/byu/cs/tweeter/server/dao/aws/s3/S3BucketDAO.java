package edu.byu.cs.tweeter.server.dao.aws.s3;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

import edu.byu.cs.tweeter.server.dao.BucketDAO;

public class S3BucketDAO implements BucketDAO {
    private final S3Facade s3;
    private final String bucketName;

    public S3BucketDAO(String region) {
        this.s3 = new S3Facade(region);
        this.bucketName = "ogden9-tweeter";
    }

    @Override
    public void putImage(String keyName, String image) {
        getS3().putObject(makeRequest(keyName, image));
    }

    private PutObjectRequest makeRequest(String keyName, String image) {
        return new PutObjectRequest(
                    getBucketName(),
                    keyName,
                    imageToStream(image),
                    new ObjectMetadata()
                ).withCannedAcl(CannedAccessControlList.PublicRead);
    }

    private InputStream imageToStream(String image) {
        byte[] imageBytes = Base64.getDecoder().decode(image);

        return new ByteArrayInputStream(imageBytes);
    }

    public String getBucketURL() {
        return getS3().getS3URL() + getBucketName();
    }

    public S3Facade getS3() {
        return s3;
    }

    public String getBucketName() {
        return bucketName;
    }
}

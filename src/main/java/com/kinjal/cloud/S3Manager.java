package com.kinjal.cloud;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class S3Manager {

    private static final String testBucket = "kinjal-task-reminder-bucket";

    // Create an S3 client using profile credentials
    private static S3Client s3Client = S3Client.builder()
            .credentialsProvider(ProfileCredentialsProvider.create()) // Use ProfileCredentialsProvider
            .region(software.amazon.awssdk.regions.Region.AP_SOUTH_1) // Set your region
            .build();

    // Create a new S3 bucket
    public static void createBucket(String bucketName) {
        try {
            CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            s3Client.createBucket(createBucketRequest);
            System.out.println("Bucket created: " + bucketName);

        } catch (S3Exception e) {
            e.printStackTrace();
        }
    }

    // Upload a file to the specified S3 bucket
    public static void uploadFile(String bucketName, String key, String filePath) {
        try {
            Path path = Paths.get("TestFile.txt");
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.putObject(putObjectRequest, path);
            System.out.println("File uploaded to S3 bucket: " + bucketName);

        } catch (S3Exception e) {
            e.printStackTrace();
        }
    }
}

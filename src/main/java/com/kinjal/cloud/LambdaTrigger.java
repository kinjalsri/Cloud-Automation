package com.kinjal.cloud;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.*;

public class LambdaTrigger {

    private static final String REGION = "ap-south-1"; // Set your AWS region
    private static final String LAMBDA_FUNCTION_NAME = "taskReminderLambda"; // Your Lambda function name

    // Create a Lambda client
    private static LambdaClient lambdaClient = LambdaClient.builder()
            .credentialsProvider(ProfileCredentialsProvider.create()) // Use profile credentials
            .region(software.amazon.awssdk.regions.Region.of(REGION))
            .build();

    // Method to invoke the Lambda function
    public static void invokeLambdaFunction(String task, String dueMinutes) {
        try {
            // Example payload
            String payload = String.format("{\"task\": \"%s\", \"dueTime\": \"%s\"}", task, dueMinutes);

            InvokeRequest invokeRequest = InvokeRequest.builder()
                    .functionName(LAMBDA_FUNCTION_NAME) // Lambda function name
                    .payload(SdkBytes.fromUtf8String(payload)) // Pass the payload
                    .build();

            InvokeResponse invokeResponse = lambdaClient.invoke(invokeRequest);

            String response = invokeResponse.payload().asUtf8String(); // Get the response as a string
            System.out.println("Lambda response: " + response);

        } catch (LambdaException e) {
            e.printStackTrace();
        }
    }
}
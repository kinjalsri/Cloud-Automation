package com.kinjal.cloud;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Map;

public class TaskReminderLambda implements RequestHandler<Map<String, String>, String> {

    private static final String TOPIC_ARN = "arn:aws:sns:ap-south-1:222634404865:task-reminder-project";

    @Override
    public String handleRequest(Map<String, String> input, Context context) {
        String task = input.get("task");
        String dueTime = input.get("dueTime");

        String message = "Reminder: Task \"" + task + "\" is due in " + dueTime;

        try (SnsClient snsClient = SnsClient.create()) {
            PublishRequest request = PublishRequest.builder()
                    .message(message)
                    .topicArn(TOPIC_ARN)
                    .build();

            PublishResponse result = snsClient.publish(request);
            System.out.println("Message sent! ID: " + result.messageId());
        }

        return "Reminder set for task: " + task + ", due in: " + dueTime;
    }
}

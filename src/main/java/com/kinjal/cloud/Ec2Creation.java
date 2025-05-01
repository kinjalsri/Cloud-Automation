package com.kinjal.cloud;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

public class Ec2Creation {
    public static void launchEC2Instance() {
        Ec2Client ec2 = Ec2Client.create();

        RunInstancesRequest runRequest = RunInstancesRequest.builder()
                .imageId("ami-0f1dcc636b69a6438") // Use correct AMI for your region
                .instanceType(InstanceType.T2_MICRO)
                .maxCount(1)
                .minCount(1)
                .build();

        ec2.runInstances(runRequest);
        System.out.println("EC2 Instance launched successfully");
    }
}
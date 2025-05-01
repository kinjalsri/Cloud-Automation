package com.kinjal.cloud;

import java.util.*;

public class TaskReminder {

    public static void main(String[] args) {
        try {
            Ec2Creation.launchEC2Instance();
            System.out.println("EC2 Instance launched successfully");
        } catch (Exception e) {
            System.out.println("Warning: Could not launch EC2 instance.");
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Task Reminder CLI");
        List<String> taskHistory = new ArrayList<>();

        while (true) {
            System.out.println("==== Task Reminder CLI ====");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Exit");
            System.out.print(">> ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 1) {
                System.out.print("Enter Task Name: ");
                String taskName = scanner.nextLine();

                System.out.print("Due in how many minutes? ");
                String dueInput = scanner.nextLine(); // safer than nextInt()
                int dueMinutes = Integer.parseInt(dueInput.trim());
                String dueTime = dueMinutes + " minutes";
                taskHistory.add("Task: " + taskName + ", Due in: " + dueTime);

                System.out.println("Task added! Uploading to S3 and triggering reminder...");

                // Upload task to S3
                String bucketName = "kinjal-task-reminder-bucket";
                String fileName = "task_" + System.currentTimeMillis() + ".txt";
                String fileContent = "Task: " + taskName + "\nDue in: " + dueTime;

                try {
                    S3Manager.uploadFile(bucketName, fileName, fileContent);
                    System.out.println("Task uploaded to S3 as: " + fileName);
                } catch (Exception e) {
                    System.out.println("Failed to upload task to S3.");
                    e.printStackTrace();
                }

                // Trigger Lambda function with actual task and due time
                LambdaTrigger.invokeLambdaFunction(taskName, dueTime);
            } else if (choice == 2) {
                if (taskHistory.isEmpty()) {
                    System.out.println("No tasks added yet.");
                } else {
                    System.out.println("Tasks:");
                    for (String task : taskHistory) {
                        System.out.println(task);
                    }
                }

            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;

            } else {
                System.out.println("Invalid option. Try again.");
            }
        }

    }
}

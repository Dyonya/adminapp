package com.example.demo.service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NotificationSender {

    public void sendNotification(String employeeAbbreviation) {

        try {
            // Set up the URL and connection
            URL url = new URL("http://localhost:8080/api/notify");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Create the request body
            String requestBody = "{\"level\":\"warning\",\"employeeAbbreviation\":\"" + employeeAbbreviation + "\",\"message\":\"some message\"}";

            // Send the request
            OutputStream os = connection.getOutputStream();
            os.write(requestBody.getBytes());
            os.flush();
            os.close();

            // Get the response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Request was successful
                System.out.println("Notification sent successfully");
            } else {
                // Request failed
                System.out.println("Failed to send notification. Response code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.smartstudentportal.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class loginController {
    private static final String API_KEY = "AIzaSyADjBCJb7FdejlUX5fG4GVL9mP3EQDqeD0";
    private static final String PROJECT_ID = "javafx-99587";

    // Sign up with Firebase Auth and return ID token or error
    public String signupUser(String email, String password) {
        String endpoint = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + API_KEY;

        String payload = String.format("""
        {
          "email": "%s",
          "password": "%s",
          "returnSecureToken": true
        }
        """, email, password);

        return sendFirebaseRequest(endpoint, payload);
    }

    // Login user with Firebase Auth
    public boolean signInWithEmailAndPassword(String email, String password) {
        String endpoint = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + API_KEY;

        String payload = String.format("""
        {
          "email": "%s",
          "password": "%s",
          "returnSecureToken": true
        }
        """, email, password);

        String response = sendFirebaseRequest(endpoint, payload);
        return response.contains("idToken");
    }

    // Add student details to Firestore
    public String addUserToFirestore(String name, String email, String mobile, String year, String exam, String state, String universityType, String university, String password) {
        if (name.isEmpty() || email.isEmpty()) {
            return "Name and Email are required.";
        }

        String endpoint = String.format(
                "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/StudentInfo?key=%s",
                PROJECT_ID, API_KEY);

        String payload = String.format("""
        {
          "fields": {
            "name": { "stringValue": "%s" },
            "email": { "stringValue": "%s" },
            "mobile": { "stringValue": "%s" },
            "passingYear": { "stringValue": "%s" },
            "examAppeared": { "stringValue": "%s" },
            "state": { "stringValue": "%s" },
            "universityType": { "stringValue": "%s" },
            "university": { "stringValue": "%s" },
            "password": { "stringValue": "%s" }
          }
        }
        """, name, email, mobile, year, exam, state, universityType, university, password);

        return sendFirebaseRequest(endpoint, payload);
    }

    // Firebase request handler
    private String sendFirebaseRequest(String endpoint, String payload) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.getBytes(StandardCharsets.UTF_8));
            }

            try (InputStream is = conn.getInputStream()) {
                byte[] responseBytes = is.readAllBytes();
                return new String(responseBytes, StandardCharsets.UTF_8);
            }

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}

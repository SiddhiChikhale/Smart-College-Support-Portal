package com.smartstudentportal.model;

import javafx.scene.control.Alert;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class adminregister {

    private static final String API_KEY = "AIzaSyADjBCJb7FdejlUX5fG4GVL9mP3EQDqeD0"; // Firebase Web API key
    private static final String PROJECT_ID = "javafx-99587"; // Firebase project ID

    // ---------------------- USER SIGN-UP ------------------------
    public static String signupUser(String email, String password) {
        String endpoint = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + API_KEY;

        String payload = String.format("""

        {
          "email": "%s",
          "password": "%s",
          "returnSecureToken": true
        }
        """, email, password);

        String response = sendFirebaseRequest(endpoint, payload);

        if (response.contains("EMAIL_EXISTS")) {
            showAlert(Alert.AlertType.ERROR, "❌ Email already registered. Please sign in.");
            return "Email already exists";
        }

        if (response.contains("idToken")) {
            showAlert(Alert.AlertType.INFORMATION, "✅ Registration successful!");
            return response;
        }

        showAlert(Alert.AlertType.ERROR, "❌ Unexpected error during registration.");
        return "Unexpected error";
    }

    // ---------------------- USER SIGN-IN ------------------------
    public static boolean signInWithEmailAndPassword(String email, String password) {
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

    // ------------------- ADD ADMIN TO FIRESTORE -------------------
    public static String addAdminToFirestore(String name, String email, String field, String state, String universityType,
                                             String university, String password) {

        String endpoint = String.format(
                "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/AdminInfo?key=%s",
                PROJECT_ID, API_KEY);

        String payload = String.format("""
        {
          "fields": {
            "collegename": { "stringValue": "%s" },
            "collegeemail": { "stringValue": "%s" },
            "field": { "stringValue": "%s" },
            "state": { "stringValue": "%s" },
            "universityType": { "stringValue": "%s" },
            "university": { "stringValue": "%s" },
            "password": { "stringValue": "%s" }
          }
        }
        """, name, email, field, state, universityType, university, password);

        String response = sendFirebaseRequest(endpoint, payload);

        if (response.contains("name")) {
            showAlert(Alert.AlertType.INFORMATION, "✅ Admin data added to Firestore.");
            return "Firestore insert success";
        }

        showAlert(Alert.AlertType.ERROR, "❌ Failed to add admin data to Firestore.");
        return "Firestore insert failed";
    }

    // ------------------------ COMMON HTTP METHOD ------------------------
    private static String sendFirebaseRequest(String endpoint, String payload) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.getBytes(StandardCharsets.UTF_8));
            }

            InputStream is = (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300)
                    ? conn.getInputStream() : conn.getErrorStream();

            byte[] responseBytes = is.readAllBytes();
            return new String(responseBytes, StandardCharsets.UTF_8);

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "❌ Request failed: " + e.getMessage());
            return "Request failed";
        }
    }

    // ------------------------ ALERT HELPER ------------------------
    private static void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Smart Student Portal");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

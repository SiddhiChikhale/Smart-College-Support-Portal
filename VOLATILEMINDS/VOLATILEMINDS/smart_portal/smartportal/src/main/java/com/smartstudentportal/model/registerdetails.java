package com.smartstudentportal.model;

import javafx.scene.control.Alert;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class registerdetails {

    private static final String API_KEY = "AIzaSyADjBCJb7FdejlUX5fG4GVL9mP3EQDqeD0"; // Replace if needed
    private static final String PROJECT_ID = "javafx-99587"; // Replace if needed

    public static String idToken = null;

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
            return "Registration successful";
        }

        showAlert(Alert.AlertType.ERROR, "❌ Unexpected error during registration.");
        return "Unexpected error";
    }

    public static String signinUser(String email, String password) {
        String endpoint = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + API_KEY;

        String payload = String.format("""
        {
          "email": "%s",
          "password": "%s",
          "returnSecureToken": true
        }
        """, email, password);

        String response = sendFirebaseRequest(endpoint, payload);

        if (response.contains("EMAIL_NOT_FOUND")) {
            showAlert(Alert.AlertType.ERROR, "❌ No account found with this email. Please register.");
            return "Email not found";
        }

        if (response.contains("INVALID_PASSWORD")) {
            showAlert(Alert.AlertType.ERROR, "❌ Incorrect password. Try again.");
            return "Invalid password";
        }

        if (response.contains("idToken")) {
            idToken = extractValue(response, "idToken");
            showAlert(Alert.AlertType.INFORMATION, "✅ Login successful!");
            return "Login successful";
        }

        showAlert(Alert.AlertType.ERROR, "❌ Unexpected error during login.");
        return "Unexpected error";
    }

    public static String fetchUserProfileByEmail(String email) {
        try {
            String endpoint = String.format(
                    "https://firestore.googleapis.com/v1/projects/%s/databases/(default)/documents/StudentInfo?key=%s",
                    PROJECT_ID, API_KEY);

            HttpURLConnection conn = (HttpURLConnection) new URL(endpoint).openConnection();
            conn.setRequestMethod("GET");

            InputStream is = conn.getResponseCode() >= 200 && conn.getResponseCode() < 300
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            String response = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            String[] documents = response.split("\\{\\s*\"name\"\\s*:");

            for (String doc : documents) {
                if (doc.contains("\"email\":{\"stringValue\":\"" + email + "\"")) {
                    String name = extractField(doc, "name");
                    String mobile = extractField(doc, "mobile");
                    String year = extractField(doc, "passingYear");
                    String exam = extractField(doc, "examAppeared");
                    String state = extractField(doc, "state");
                    String type = extractField(doc, "universityType");
                    String university = extractField(doc, "university");

                    return String.format("""
                            👤 Name: %s
                            📧 Email: %s
                            📱 Mobile: %s
                            🎓 Passing Year: %s
                            📝 Exam Appeared: %s
                            🌐 State: %s
                            🏛️ University Type: %s
                            🏫 University: %s
                            """, name, email, mobile, year, exam, state, type, university);
                }
            }

            return "❌ Profile not found in Firestore.";
        } catch (Exception e) {
            return "❌ Error fetching profile: " + e.getMessage();
        }
    }

    private static String extractField(String doc, String key) {
        try {
            String search = "\"" + key + "\":{\"stringValue\":\"";
            int start = doc.indexOf(search);
            if (start == -1) return "N/A";
            start += search.length();
            int end = doc.indexOf("\"", start);
            return doc.substring(start, end);
        } catch (Exception e) {
            return "N/A";
        }
    }

    private static String extractValue(String json, String key) {
        try {
            String search = "\"" + key + "\":\"";
            int start = json.indexOf(search);
            if (start == -1) return null;
            start += search.length();
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        } catch (Exception e) {
            return null;
        }
    }

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

            InputStream is;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {
                is = conn.getInputStream();
            } else {
                is = conn.getErrorStream();
            }

            byte[] responseBytes = is.readAllBytes();
            return new String(responseBytes, StandardCharsets.UTF_8);

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "❌ Request failed: " + e.getMessage());
            return "Request failed";
        }
    }

    private static void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Student Portal");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}

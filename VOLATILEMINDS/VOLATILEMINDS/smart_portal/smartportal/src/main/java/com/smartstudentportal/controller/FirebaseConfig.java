package com.smartstudentportal.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class FirebaseConfig {
    static {

        initialize();
    }

    public static void initialize() {
        try {
            FileInputStream serviceAccount = new FileInputStream(
                    "smart_portal\\smartportal\\src\\main\\resources\\javafx-99587-firebase-adminsdk-fbsvc-92da7d8f6a.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Firestore getFirestoreObject() {
        return FirestoreClient.getFirestore();
    }
}

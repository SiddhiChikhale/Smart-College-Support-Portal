



package com.smartstudentportal.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class aicontroller extends VBox {

    private static final String API_KEY = "AIzaSyBiuPPEHaD6gYsGmKBZgtic8jR7nMhdpWM";
    private static final String ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;

    private VBox chatBox;
    private ScrollPane scrollPane;
    private TextField userInput;

    private final List<String> chatHistory = new ArrayList<>();

    public aicontroller() {
        setPadding(new Insets(20));
        setSpacing(20);
        setAlignment(Pos.TOP_CENTER);

        // Chat area setup
        chatBox = new VBox(15);
        chatBox.setPadding(new Insets(20));
        chatBox.setStyle("-fx-background-color: transparent;");

        scrollPane = new ScrollPane(chatBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(500);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // User input
        userInput = new TextField();
        userInput.setPromptText("Ask me Anything......");
        userInput.setPrefWidth(800);
        userInput.setStyle("""
        -fx-background-color: #ececec;
        -fx-background-radius: 20;
        -fx-border-radius: 20;
        -fx-text-fill: #333333;
        -fx-padding: 10;
        -fx-font-size: 14px;
    """);

        // Allow pressing Enter to send
        userInput.setOnAction(e -> handleSend());

        // Send button
        Button sendButton = new Button("Send");
        sendButton.setStyle("""
            -fx-background-color: linear-gradient(to right, #2196F3, #21CBF3);
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-radius: 20;
            -fx-padding: 8 20;
        """);
        sendButton.setOnAction(e -> handleSend());

        HBox inputBox = new HBox(10, userInput, sendButton);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(10));

        // Title
        Label title = new Label("🤖 Smart College AI Chatbot");
        title.setFont(Font.font("Arial", 24));
        title.setTextFill(Color.web("#066affff"));
        title.setAlignment(Pos.CENTER);

        this.getChildren().addAll(title, inputBox, scrollPane);
    }

    private void handleSend() {
        String question = userInput.getText().trim();
        if (!question.isEmpty()) {
            addMessage("You", question, "#f5f8faff", Pos.CENTER_RIGHT);
            chatHistory.add("You: " + question);
            userInput.clear();

            new Thread(() -> {
                String response = sendToGeminiAPI(question);
                javafx.application.Platform.runLater(() -> {
                    addMessage("Gemini", response, "#f5eeeeff", Pos.CENTER_LEFT);
                    chatHistory.add("Gemini: " + response);
                });
            }).start();
        }
    }

    private void addMessage(String sender, String text, String bgColor, Pos alignment) {
        VBox messageBox = new VBox();
        messageBox.setAlignment(alignment);
        messageBox.setPadding(new Insets(5));

        Label label = new Label(sender + ":");
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: Blue;");

        Text messageText = new Text(text);
        messageText.setWrappingWidth(400);
        messageText.setStyle("-fx-font-size: 14px;");

        VBox bubble = new VBox(label, messageText);
        bubble.setPadding(new Insets(10));
        bubble.setStyle("-fx-background-color: " + bgColor + "; -fx-background-radius: 15;");
        messageBox.getChildren().add(bubble);

        chatBox.getChildren().add(messageBox);
        scrollPane.setVvalue(1.0);
    }

    private String sendToGeminiAPI(String prompt) {
        try {
            URL url = new URL(ENDPOINT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInput = """
                {
                  "contents": [
                    {
                      "parts": [
                        {
                          "text": "%s"
                        }
                      ]
                    }
                  ]
                }
                """.formatted(prompt);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();
            InputStream is = (status == 200) ? conn.getInputStream() : conn.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                response.append(line);
            reader.close();

            String raw = response.toString();
            int index = raw.indexOf("\"text\":");
            if (index != -1) {
                String extracted = raw.substring(index + 8);
                extracted = extracted.split("\"")[1];
                return extracted.replace("\\n", "\n");
            } else {
                return "No valid response from Gemini.";
            }

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private void saveChatToFile() {
        try (PrintWriter writer = new PrintWriter("chat_history.txt")) {
            for (String line : chatHistory) {
                writer.println(line);
            }
            System.out.println("Chat saved to chat_history.txt");
        } catch (IOException e) {
            System.out.println("Error saving chat: " + e.getMessage());
        }
    }
}
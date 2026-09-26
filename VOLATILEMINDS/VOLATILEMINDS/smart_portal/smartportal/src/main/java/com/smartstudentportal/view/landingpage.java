package com.smartstudentportal.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.smartstudentportal.controller.CollegeController;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class landingpage extends Application {
    VBox mainContent;

   @Override
public void start(Stage primaryStage) {
    BorderPane rootPane = new BorderPane();

    rootPane.setStyle(
    
    "-fx-background-repeat: no-repeat;" +
    "-fx-background-size: cover;" +
    "-fx-background-position: center center;"
);
    // Background image
    Image bgImage = new Image(getClass().getResourceAsStream("/images/college.jpg"));
    BackgroundImage backgroundImage = new BackgroundImage(
        bgImage,
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.CENTER,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
    );

    Pane bgPane = new Pane();
    bgPane.setBackground(new Background(backgroundImage));

    Region dimLayer = new Region();
    dimLayer.setStyle("-fx-background-color: rgba(255, 255, 255, 0.4);");
    dimLayer.prefWidthProperty().bind(rootPane.widthProperty());
    dimLayer.prefHeightProperty().bind(rootPane.heightProperty());

    StackPane stack = new StackPane(bgPane, dimLayer, rootPane);

    // --- Sidebar (Transparent)
    VBox sidebar = new VBox(20);
    sidebar.setPadding(new Insets(30, 20, 30, 20));
    sidebar.setStyle("""
    -fx-border-color: rgba(14, 118, 236, 0.86);
    -fx-border-radius: 10;
""");

    sidebar.setPrefWidth(220);
    sidebar.setAlignment(Pos.TOP_CENTER);

    Label title = new Label("Dashboard");
    title.setFont(Font.font("Arial", FontWeight.BOLD, 26));
    title.setTextFill(Color.WHITE);
    title.setMaxWidth(Double.MAX_VALUE);
    title.setAlignment(Pos.CENTER);
    title.setPadding(new Insets(10, 0, 20, 0));
    //title.setStyle("-fx-background-color: rgba(26,188,156,0.8); -fx-background-radius: 12;");

    Image logoImg = new Image(getClass().getResourceAsStream("/images/login2.png"));
    ImageView logoView = new ImageView(logoImg);
    logoView.setFitWidth(60);
    logoView.setFitHeight(60);

    Button btnHome = new Button("🏠  Home");
    Button btnaboutus = new Button("⚙️  About Us");
    Button btnAssistant = new Button("💬  Assistant");
    Button btnLogout = new Button("🚪  Logout");

    sidebar.getChildren().addAll(logoView, title, btnHome, btnaboutus, btnAssistant, btnLogout);
    rootPane.setLeft(sidebar);

    
    HBox topbar = new HBox();
    topbar.setPadding(new Insets(20, 30, 20, 30));
   // topbar.setStyle("-fx-background-color: rgba(74,170,235,0.5); -fx-background-radius: 0 0 10 10;");
    topbar.setAlignment(Pos.CENTER_LEFT);
    topbar.setSpacing(20);

    Label welcomeLabel = new Label("👋 Welcome to Smart Student Portal");
    welcomeLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
    welcomeLabel.setTextFill(Color.web("#001F3F"));

    topbar.getChildren().add(welcomeLabel);
    rootPane.setTop(topbar);

    mainContent = new VBox(30);
    mainContent.setAlignment(Pos.TOP_CENTER);
    mainContent.setOpacity(0.6);
    mainContent.setPadding(new Insets(60, 40, 40, 40));
    mainContent.setStyle(
    "-fx-border-color: rgba(11, 119, 243, 0.94);" +      
    "-fx-border-radius: 10;" +
    "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.2), 10, 0, 0, 4);"
);
   

    ScrollPane scrollPane = new ScrollPane(mainContent);
    scrollPane.setFitToWidth(true);
    scrollPane.setStyle("-fx-background-color: transparent;");
    rootPane.setCenter(mainContent);

    // Event Handling
    btnHome.setOnAction(e -> setHomeContent());
    btnaboutus.setOnAction(e -> {
        mainContent.getChildren().clear();
        AboutPage aboutPage = new AboutPage();
        Runnable backToMain = () -> setHomeContent();
        mainContent.getChildren().add(aboutPage.createScene(backToMain));
    });
    btnAssistant.setOnAction(e -> {
        mainContent.getChildren().clear();
        aicontroller aiView = new aicontroller();
        mainContent.getChildren().add(aiView);
    });
    btnLogout.setOnAction(e -> {
        new mainscreen().start(primaryStage);
        mainContent.getChildren().clear();
        Label logoutLabel = new Label("🚪 You have been logged out.");
        logoutLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        logoutLabel.setTextFill(Color.RED);
        mainContent.getChildren().add(logoutLabel);
    });

    setHomeContent();

    Scene scene = new Scene(stack, 1300, 700);
    primaryStage.setTitle("Student Dashboard");
    primaryStage.setScene(scene);
    primaryStage.show();
}


    private void setMainContent(String contentText) {
        mainContent.getChildren().clear();
        Label contentLabel = new Label(contentText);
        contentLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 18));
       // contentLabel.setTextFill(Color.web("#2C3E50"));
        contentLabel.setWrapText(true);
        mainContent.getChildren().add(contentLabel);
    }

    private void setHomeContent() {
        mainContent.getChildren().clear();

        // Label searchLabel = new Label("🔍 Search by College Name:");
        // searchLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        // searchLabel.setTextFill(Color.web("#2C3E50")); 
        // searchLabel.setStyle("-fx-background-color: rgba(104,171,238,0.5); -fx-background-radius: 15;");


        // ObservableList<String> collegeNames = FXCollections.observableArrayList(
        //         "MIT Pune", "COEP", "VIT Pune", "IIT Bombay", "SPPU",
        //         "PCCOE", "PICT", "Sinhgad Institute", "DY Patil College", "BVCOE"
        // );

        // ChoiceBox<String> collegeChoiceBox = new ChoiceBox<>(collegeNames);
        // collegeChoiceBox.setPrefWidth(300);
        // collegeChoiceBox.setStyle("-fx-font-size: 14px; -fx-background-radius: 10; -fx-border-color: #1ABC9C;");
        // collegeChoiceBox.setValue("Select College");

        // VBox searchSection = new VBox(10, searchLabel, collegeChoiceBox);
        // searchSection.setAlignment(Pos.CENTER);
    //     searchSection.setStyle("""
    //     -fx-background-color: rgba(255, 255, 255, 0.6); 
    //     -fx-padding: 20; 
    //     -fx-border-color: #1ABC9C; 
    //     -fx-border-radius: 10; 
    //     -fx-background-radius: 10;
    // """);
        //mainContent.getChildren().add(searchSection);

        Region spacer = new Region();
        spacer.setMinHeight(20);
        mainContent.getChildren().add(spacer);

     // VBox to hold rows (HBox) of college cards
VBox collegeRowsBox = new VBox(60); // Vertical spacing between rows
collegeRowsBox.setAlignment(Pos.TOP_CENTER);
collegeRowsBox.setPadding(new Insets(30));

//collegeRowsBox.setStyle("-fx-background-color: rgba(104,171,238,0.5); -fx-background-radius: 15;");

// ScrollPane with both vertical and horizontal scrollbars
ScrollPane scrollPane = new ScrollPane(collegeRowsBox);
scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
scrollPane.setFitToWidth(true);
scrollPane.setStyle("-fx-background-color: transparent;");
scrollPane.setPrefViewportHeight(500);
scrollPane.setPrefViewportWidth(900); // Wider width for 4 cards

List<Map<String, Object>> list = new ArrayList<>();
try {
    list = CollegeController.getCollegeData(); // Fetch college list
} catch (InterruptedException | ExecutionException e) {
    e.printStackTrace();
}

// ✅ Set to 4 colleges per row
int cardsPerRow = 4;
HBox currentRow = new HBox(50); // Horizontal spacing between cards
currentRow.setAlignment(Pos.CENTER_LEFT);

for (int i = 0; i < list.size(); i++) {
    Map<String, Object> college = list.get(i);

    
    // Create square college card
    VBox collegeCard = new VBox(6);
    collegeCard.setPadding(new Insets(12));
    collegeCard.setAlignment(Pos.CENTER);
    collegeCard.setStyle("""
    
    -fx-background-radius: 12;
    -fx-border-color: #0979c4ff;
    -fx-border-radius: 12;

    """);

    collegeCard.setPrefSize(160, 160); // Slightly smaller square size
    collegeCard.setMaxSize(160, 160);

    String name = (String) college.get("name");
    String desc = (String) college.get("desc");
    double rating = 4.0 + i * 0.1;

    Label collegeName = new Label("🏫 " + name);
    collegeName.setFont(Font.font("Arial", FontWeight.BOLD, 13));
    collegeName.setWrapText(true);
    collegeName.setTextAlignment(TextAlignment.CENTER);
    collegeName.setTextFill(Color.web("#070808ff"));

    Label locationLabel = new Label("📍 " + desc);
    locationLabel.setFont(Font.font("Arial", 12));
    locationLabel.setWrapText(true);
    locationLabel.setTextAlignment(TextAlignment.CENTER);
    locationLabel.setTextFill(Color.web("#0a0a0aff"));

    Label ratingLabel = new Label("⭐ " + String.format("%.1f", rating));
    ratingLabel.setFont(Font.font("Arial", 12));
    ratingLabel.setTextFill(Color.web("#00060cff"));

    collegeCard.getChildren().addAll(collegeName, locationLabel, ratingLabel);

  collegeCard.setOnMouseClicked(event -> {
    collegepage cp = new collegepage(college);
    cp.openCollegePage();
    collegeCard.setStyle(
        "-fx-background-radius: 12;" +
        "-fx-background-color: rgba(128, 206, 236, 0.85);"+
        "-fx-border-color: #3498DB;" +
     "-fx-border-radius: 12;" +
        "-fx-cursor: hand;" +  // Pointer cursor
        "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.3), 10, 0, 0, 4);"
    );

    ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), collegeCard);
    scaleUp.setToX(1.05);
    scaleUp.setToY(1.05);
    scaleUp.play();
});

collegeCard.setOnMouseExited(event -> {
    collegeCard.setStyle(
        "-fx-background-color: rgba(202, 186, 186, 0.85);" +
        "-fx-background-radius: 12;" +
        "-fx-border-color: rgba(52, 152, 219, 0.3);" +
        "-fx-border-radius: 12;" +
        "-fx-effect: dropshadow(two-pass-box, rgba(0, 0, 0, 0.1), 4, 0, 0, 2);"
    );

    ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), collegeCard);
    scaleDown.setToX(1.0);
    scaleDown.setToY(1.0);
    scaleDown.play();
});
collegeCard.setOnMouseEntered(event -> {
    collegeCard.setStyle(
        "-fx-background-color: rgba(93, 217, 248, 0.85);" +
        "-fx-background-radius: 12;" +
        "-fx-border-color: rgba(52, 152, 219, 0.3);" +
        "-fx-border-radius: 12;" +
        "-fx-effect: dropshadow(two-pass-box, rgba(0, 0, 0, 0.1), 4, 0, 0, 2);"
    );

    ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), collegeCard);
    scaleDown.setToX(1.0);
    scaleDown.setToY(1.0);
    scaleDown.play();
});


    // Add to row
    currentRow.getChildren().add(collegeCard);

    // Add row to VBox if full or at end
    if ((i + 1) % cardsPerRow == 0 || i == list.size() - 1) {
        collegeRowsBox.getChildren().add(currentRow);
        currentRow = new HBox(50); // Same spacing for new row
        currentRow.setAlignment(Pos.CENTER_LEFT);

    }
}

// Section title
Label titleLabel = new Label("🔥 Top Colleges:");
titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
//titleLabel.setTextFill(Color.web("#1F618D"));

// Add to main content
mainContent.getChildren().addAll(titleLabel, scrollPane);
    }
}

package com.smartstudentportal.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Map;

public class collegepage {

    VBox mainContent;
    Map<String, Object> map;
    String str = "";

    public collegepage(Map<String, Object> map) {
        this.map = map;
        str = map.get("name").toString();
    }

    public void openCollegePage() {
        Stage primaryStage = new Stage();
        mainContent = new VBox();
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setPadding(new Insets(30));
        mainContent.setSpacing(20);
        mainContent.setStyle(
            "-fx-background-color: rgba(255, 255, 255, 0.1);" +
            "-fx-background-radius: 15;" +
            "-fx-border-color: #2980b9;" +
            "-fx-border-radius: 15;" +
            "-fx-border-width: 2;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0.3, 0, 5);"
        );

        // Navigation Bar
        HBox navBar = new HBox(20);
        navBar.setPadding(new Insets(15));
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setStyle(
            "-fx-background-color: rgba(44, 62, 80, 0.8);" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0.3, 0, 2);"
        );

        Button bnhome = createNavButton("Home");
        Button btnFeatures = createNavButton("College Features");
        Button btnAdmission = createNavButton("Admission Process");
        Button btnBranch = createNavButton("Branch Selection");
        Button backButton = createNavButton("Back");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        navBar.getChildren().addAll(bnhome, btnFeatures, btnAdmission, btnBranch, spacer, backButton);

        VBox root = new VBox(navBar, mainContent);
        root.setSpacing(20);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.TOP_CENTER);

        // Background Image
        BackgroundImage bgImage = new BackgroundImage(
            new Image("/images/college.jpg", 1300, 800, false, true),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            BackgroundSize.DEFAULT
        );
        root.setBackground(new Background(bgImage));

        Scene scene = new Scene(root, 1300, 700);
        primaryStage.setTitle("College Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load initial home page
        loadHomePage();

        bnhome.setOnAction(e -> loadHomePage());
        btnFeatures.setOnAction(e -> loadFeaturesPage());
        btnAdmission.setOnAction(e -> loadAdmissionPage());
        btnBranch.setOnAction(e -> loadBranchPage());
        backButton.setOnAction(e -> new landingpage().start(primaryStage));
    }

    private Button createNavButton(String title) {
        Button button = new Button(title);
        button.setStyle(
            "-fx-background-color: #82baf1ff;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;"
        );
        button.setOnMouseEntered(e -> button.setStyle(
            "-fx-background-color: #000108ff;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
            "-fx-background-color: #3084d8ff;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-background-radius: 6;"
        ));
        button.setPrefHeight(40);
        return button;
    }

    private void loadHomePage() {
        mainContent.getChildren().clear();

        VBox homeBox = new VBox(20);
        homeBox.setAlignment(Pos.CENTER);
        homeBox.setPadding(new Insets(50));
        homeBox.setStyle(
            "-fx-background-color: rgba(255, 255, 255, 0.2);" +
            "-fx-border-color: white;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 12;" +
            "-fx-background-radius: 12;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 8, 0.2, 0, 4);"
        );

        Text welcomeText = new Text("🏫 Welcome to " + str);
        welcomeText.setFont(Font.font("Verdana", 32));
        welcomeText.setFill(Color.BLACK);

        homeBox.getChildren().add(welcomeText);
        mainContent.getChildren().add(homeBox);
    }

    private void loadFeaturesPage() {
        mainContent.getChildren().clear();

        String featuresData = map.getOrDefault("featuredata", "No feature data available.").toString();

        VBox page = createStandardPage("🎓 College Features", featuresData);
        mainContent.getChildren().add(page);
    }

    private void loadAdmissionPage() {
        mainContent.getChildren().clear();

        StringBuilder st = new StringBuilder();
        st.append(map.getOrDefault("Branch-Wise Intake (Sample Colleges)", "")).append("\n\n")
          .append(map.getOrDefault("CAP Rounds (Centralized Admission Process)", "")).append("\n\n")
          .append(map.getOrDefault("Documents Required for Admission", "")).append("\n\n")
          .append(map.getOrDefault("Past Year Cutoff Examples (General Category)", ""));

        VBox page = createStandardPage("📝 Admission Process", st.toString());
        mainContent.getChildren().add(page);
    }

    private void loadBranchPage() {
        mainContent.getChildren().clear();

        StringBuilder stt = new StringBuilder();
        stt.append(map.getOrDefault("branchName", "")).append("\n\n")
           .append(map.getOrDefault("careerArea", "")).append("\n\n")
           .append(map.getOrDefault("Electronics and Telecommunication Engineering (ENTC)", ""));

        VBox page = createStandardPage("📚 Branch Selection", stt.toString());
        mainContent.getChildren().add(page);
    }

    private VBox createStandardPage(String titleText, String content) {
        VBox page = new VBox(20);
        page.setAlignment(Pos.TOP_CENTER);
        page.setPadding(new Insets(30));
        page.setStyle(
            "-fx-background-color: rgba(255, 255, 255, 0.15);" +
            "-fx-background-radius: 15;" +
            "-fx-border-color: white;" +
            "-fx-border-radius: 15;" +
            "-fx-border-width: 2;"
        );

        Text title = new Text(titleText);
        title.setFont(Font.font("Verdana", 24));
        title.setFill(Color.BLACK);

        TextField collegeName = new TextField(str);
        collegeName.setAlignment(Pos.CENTER);
        collegeName.setFont(Font.font("Verdana", 18));
        collegeName.setEditable(false);
        collegeName.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: black;");

        TextArea area = new TextArea(content);
        area.setWrapText(true);
        area.setEditable(false);
        area.setPrefWidth(800);
        area.setPrefHeight(400);
        area.setFont(Font.font("Verdana", 18));

        page.getChildren().addAll(collegeName, title, area);
        return page;
    }
}


// package com.smartstudentportal.view;

// import com.smartstudentportal.controller.loginController;
// import javafx.application.Application;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Scene;
// import javafx.scene.control.*;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.layout.*;
// import javafx.scene.paint.Color;
// import javafx.scene.text.Font;
// import javafx.scene.text.FontWeight;
// import javafx.stage.Stage;

// public class mainscreen extends Application {

//     VBox centerWrapper;
//     loginController fb = new loginController();
//     Label messageLabel = new Label();

//     @Override
//     public void start(Stage stage) {
//         // Sidebar
//         VBox sidebar = new VBox(20);
//         sidebar.setPadding(new Insets(30));
//         sidebar.setPrefWidth(220);
//         sidebar.setStyle("-fx-background-color: rgba(0,0,0,0.4); -fx-background-radius: 10;");

//         Image logoImage = new Image(getClass().getResourceAsStream("/images/login2.png"));
//         ImageView logo2 = new ImageView(logoImage);
//         logo2.setFitWidth(120);
//         logo2.setPreserveRatio(true);

//         Label logo = new Label("SCSP");
//         logo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
//         logo.setTextFill(Color.WHITE);
//         logo.setMaxWidth(Double.MAX_VALUE);
//         logo.setAlignment(Pos.CENTER);

//         Label studentLabel = new Label("Student");
//         studentLabel.setTextFill(Color.WHITE);
//         Button btnCreateAccount = new Button("Create Account");
//         Button btnStudentLogin = new Button("Student Login");

//         Label adminLabel = new Label("Admin");
//         adminLabel.setTextFill(Color.WHITE);
//         Button btnCreateAccount2 = new Button("Create Account");
//         Button btnAdminLogin = new Button("Admin Login");

//         for (Button btn : new Button[]{btnCreateAccount, btnStudentLogin, btnCreateAccount2, btnAdminLogin}) {
//             btn.setMaxWidth(Double.MAX_VALUE);
//             btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 13px;");
//             btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
//             btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 13px;"));
//         }

//         sidebar.getChildren().addAll(logo2, logo, new Separator(), studentLabel, btnCreateAccount, btnStudentLogin,
//                 new Separator(), adminLabel, btnCreateAccount2, btnAdminLogin);

//         // Center content wrapper
//         centerWrapper = new VBox();
//         centerWrapper.setAlignment(Pos.CENTER);
//         centerWrapper.setPadding(new Insets(30));

//         ScrollPane mainPane = new ScrollPane(centerWrapper);
//         mainPane.setFitToWidth(true);
//         mainPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

//         // Actions
//         btnCreateAccount.setOnAction(e -> showCreateAccountForm());
//         btnStudentLogin.setOnAction(e -> showStudentLoginForm());
//         btnCreateAccount2.setOnAction(e -> showCreateAccountForm2());
//         btnAdminLogin.setOnAction(e -> showAdminLoginForm());

//         showWelcomeScreen();

//         // Main layout
//         BorderPane root = new BorderPane();
//         root.setLeft(sidebar);
//         root.setCenter(mainPane);
//         root.setOpacity(0.95);

//         // Background image setup
//         Image image = new Image(getClass().getResource("/images/college.jpg").toExternalForm());
//         BackgroundImage backgroundImage = new BackgroundImage(
//                 image,
//                 BackgroundRepeat.NO_REPEAT,
//                 BackgroundRepeat.NO_REPEAT,
//                 BackgroundPosition.CENTER,
//                 new BackgroundSize(100, 100,true , true, true, true)
//         );

//         StackPane stack = new StackPane();
//         stack.setBackground(new Background(backgroundImage));
//         stack.getChildren().add(root);

//         // Scene
//         Scene scene = new Scene(stack, 1000, 600);
//         stage.setScene(scene);
//         stage.setTitle("Smart College Support Portal");
//         stage.show();
//     }

//     private VBox createFormWrapper(String titleText) {
//         VBox form = new VBox(15);
//         form.setPadding(new Insets(25));
//         form.setStyle("-fx-background-color: rgba(255,255,255,0.4); -fx-background-radius: 10;");
//         form.setMaxWidth(600);

//         Label logo = new Label(titleText);
//         logo.setFont(Font.font("Arial", 22));
//         form.getChildren().add(logo);
//         return form;
//     }

//     private void showCreateAccountForm() {
//         VBox form = createFormWrapper("Student Registration");
//         form.getChildren().addAll(
//                 new TextField("Full Name"), new TextField("Email"), new TextField("Mobile Number"),
//                 new TextField("Passing Year"), new TextField("Exam Appeared"), new TextField("Field"),
//                 new TextField("State"), new TextField("University Type"), new TextField("University"),
//                 new PasswordField(), new Button("Register"), messageLabel
//         );
//         centerWrapper.setAlignment(Pos.CENTER);
//         centerWrapper.getChildren().setAll(form);
//     }

//     private void showStudentLoginForm() {
//         VBox loginBox = createFormWrapper("Student Login");
//         loginBox.getChildren().addAll(new TextField("Email"), new PasswordField(), new Button("Login"));
//         centerWrapper.setAlignment(Pos.CENTER);
//         centerWrapper.getChildren().setAll(loginBox);
//     }

//     private void showCreateAccountForm2() {
//         VBox form = createFormWrapper("Admin Registration");

//         TextField collegeNameField = new TextField();
//         collegeNameField.setPromptText("College Name");

//         TextField collegeEmailField = new TextField();
//         collegeEmailField.setPromptText("College Email");

//         TextField fieldField = new TextField();
//         fieldField.setPromptText("Field");

//         TextField stateField = new TextField();
//         stateField.setPromptText("State");

//         TextField universityTypeField = new TextField();
//         universityTypeField.setPromptText("University Type");

//         TextField universityField = new TextField();
//         universityField.setPromptText("University");

//         PasswordField passwordField = new PasswordField();
//         passwordField.setPromptText("Password");

//         Button registerBtn = new Button("Register");

//         form.getChildren().addAll(
//                 collegeNameField, collegeEmailField, fieldField, stateField,
//                 universityTypeField, universityField, passwordField,
//                 registerBtn, messageLabel
//         );

//         centerWrapper.setAlignment(Pos.CENTER);
//         centerWrapper.getChildren().setAll(form);
//     }

//     private void showAdminLoginForm() {
//         VBox loginBox = createFormWrapper("Admin Login");
//         loginBox.getChildren().addAll(new TextField("Username"), new PasswordField(), new Button("Login"));
//         centerWrapper.setAlignment(Pos.CENTER);
//         centerWrapper.getChildren().setAll(loginBox);
//     }

//     private void showWelcomeScreen() {
//         VBox welcomeBox = new VBox(50);
//         welcomeBox.setAlignment(Pos.CENTER);

//         Label title = new Label("Welcome to Smart College Support Portal");
//         title.setFont(Font.font("Arial", 24));
//         title.setTextFill(Color.BLACK);

//         Label desc = new Label("Connect with universities, register for courses, and explore career options.");
//         desc.setWrapText(true);
//         desc.setFont(Font.font("Arial", 20));
//         desc.setTextFill(Color.DARKSLATEGRAY);

//         welcomeBox.getChildren().addAll(title, desc);
//         centerWrapper.setAlignment(Pos.CENTER);
//         centerWrapper.getChildren().setAll(welcomeBox);
//     }
// }

package com.smartstudentportal.view;

import com.smartstudentportal.controller.loginController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class mainscreen extends Application {

    VBox centerWrapper;
    loginController fb = new loginController();
    Label messageLabel = new Label();

    @Override
    public void start(Stage stage) {
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(30));
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: rgba(0,0,0,0,4); -fx-background-radius: 10;");

        Label logo = new Label("SCSP");
        logo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        logo.setTextFill(Color.WHITE);
        logo.setMaxWidth(Double.MAX_VALUE);
        logo.setAlignment(Pos.CENTER);
        logo.setPadding(new Insets(10, 0, 20, 0));
        logo.setStyle("-fx-background-color: #1ABC9C; -fx-background-radius: 8;");

        Image logoImage = new Image(getClass().getResourceAsStream("/images/login2.png"));
        ImageView logo2 = new ImageView(logoImage);
        logo2.setFitWidth(120);
        logo2.setPreserveRatio(true);

        Label studentLabel = new Label("Student");
        studentLabel.setStyle("-fx-text-fill: #ecf0f1; -fx-font-weight: bold; -fx-font-size: 14px;");
        Button btnCreateAccount = new Button("Create Account");
        Button btnStudentLogin = new Button("Student Login");

        Label adminLabel = new Label("Admin");
        adminLabel.setStyle("-fx-text-fill: #ecf0f1; -fx-font-weight: bold; -fx-font-size: 14px;");
        Button btnCreateAccount2 = new Button("Create Account");
        Button btnAdminLogin = new Button("Admin Login");

        for (Button btn : new Button[]{btnCreateAccount, btnStudentLogin, btnCreateAccount2, btnAdminLogin}) {
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ecf0f1; -fx-font-size: 13px;");
            btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #ecf0f1; -fx-text-fill: #2980b9;"));
            btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ecf0f1; -fx-font-size: 13px;"));
        }

        sidebar.getChildren().addAll(
                logo2, logo, new Separator(), studentLabel, btnCreateAccount, btnStudentLogin,
                new Separator(), adminLabel, btnCreateAccount2, btnAdminLogin
        );

       centerWrapper = new VBox();
centerWrapper.setAlignment(Pos.TOP_CENTER);
centerWrapper.setPadding(new Insets(30));
//centerWrapper.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7);"); // ✅ Transparent white background

        

        ScrollPane mainPane = new ScrollPane(centerWrapper);
        mainPane.setFitToWidth(true);
      //  mainPane.setStyle("-fx-background-color: #f4f6f7;");
        //mainPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainPane.setStyle("-fx-background:transparent; -fx-background-color: transparent");
       // mainPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        btnCreateAccount.setOnAction(e -> showCreateAccountForm());
        btnStudentLogin.setOnAction(e -> showStudentLoginForm());
        btnCreateAccount2.setOnAction(e -> showCreateAccountForm2());
        btnAdminLogin.setOnAction(e -> showAdminLoginForm());

        showWelcomeScreen();

        
        BorderPane root = new BorderPane();
        root.setLeft(sidebar);
        root.setOpacity(0.9);
        root.setCenter(mainPane);
       // root.setStyle("-fx-background-color: rgba(255,255,255,0.65); -fx-background-radius: 15;"); // Transparent root

        // Background Image Layer
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/college.jpg"));
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
        );

        Pane bgPane = new Pane();
    bgPane.setBackground(new Background(bgImage));
        
        StackPane stack = new StackPane(bgPane,root);

        Scene scene = new Scene(stack, 1300, 700);
        stage.setScene(scene);
        stage.setTitle("Smart College Support Portal");
        stage.show();
    }

    private VBox createFormWrapper(String titleText) {
        VBox form = new VBox(15);
        form.setPadding(new Insets(25));
       // form.setStyle("-fx-background-color: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        form.setMaxWidth(600);

        Label logo = new Label(titleText);
        logo.setFont(Font.font("Arial", 22));
        logo.setTextFill(Color.web("#2c3e50"));

        form.getChildren().add(logo);
        return form;
    }

    private void showCreateAccountForm() {
        VBox form = createFormWrapper("Student Registration");

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField mobileField = new TextField();
        mobileField.setPromptText("Mobile Number");

        TextField passingYear = new TextField();
        passingYear.setPromptText("Passing Year");

        TextField examAppeared = new TextField();
        examAppeared.setPromptText("Exam Appeared");

        TextField field = new TextField();
        field.setPromptText("Field");

        TextField state = new TextField();
        state.setPromptText("State");

        TextField universityType = new TextField();
        universityType.setPromptText("University Type");

        TextField university = new TextField();
        university.setPromptText("University");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button registerBtn = new Button("Register");
        registerBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
        registerBtn.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();
            String name = nameField.getText();
            String mobile = mobileField.getText();
            String year = passingYear.getText();
            String exam = examAppeared.getText();
            String stateVal = state.getText();
            String uniType = universityType.getText();
            String universityName = university.getText();

            String signupResult = fb.signupUser(email, password);
            if (signupResult.contains("idToken")) {
                String firestoreResult = fb.addUserToFirestore(name, email, mobile, year, exam, stateVal, universityName, universityName, password);
                messageLabel.setText("✅ Registered Successfully");
                messageLabel.setTextFill(Color.WHITE);
            } else {
                messageLabel.setText("❌ Registration Failed");
                messageLabel.setTextFill(Color.WHITE);
            }
        });

        form.getChildren().addAll(
                nameField, emailField, mobileField, passingYear, examAppeared,
                field, state, universityType, university, passwordField, registerBtn, messageLabel
        );
        // titleText.setTextFill(Color.DARKBLUE); // or any color you want
        centerWrapper.setAlignment(Pos.CENTER);

        centerWrapper.getChildren().setAll(form);
    }

    private void showStudentLoginForm() {
        VBox loginBox = createFormWrapper("Student Login");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");

        loginBtn.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            boolean success = fb.signInWithEmailAndPassword(email, password);
            messageLabel.setText(success ? "✅ SignIn Successful" : "❌ SignIn Failed");

            if (success) {
                try {
                    new landingpage().start(new Stage());
                    ((Stage) loginBtn.getScene().getWindow()).close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                emailField.clear();
                passwordField.clear();

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText("Sign In Failed");
                alert.setContentText("Invalid email or password. Please try again.");
                alert.showAndWait();
            }
        });

        loginBox.getChildren().addAll(emailField, passwordField, loginBtn);
        centerWrapper.getChildren().setAll(loginBox);
        loginBox.setAlignment(Pos.CENTER);
    }

    private void showCreateAccountForm2() {
        VBox form = createFormWrapper("Admin Registration");

        TextField nameField = new TextField();
        nameField.setPromptText("College Name");

        TextField emailField = new TextField();
        emailField.setPromptText("College Email");

        TextField field = new TextField();
        field.setPromptText("Field");

        TextField state = new TextField();
        state.setPromptText("State");

        TextField universityType = new TextField();
        universityType.setPromptText("University Type");

        TextField university = new TextField();
        university.setPromptText("University");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Label messageLabel = new Label();

        Button registerBtn = new Button("Register");
        registerBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
        registerBtn.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();
            String name = nameField.getText();
            String stateVal = state.getText();
            String uniType = universityType.getText();
            String universityName = university.getText();

            String signupResult = fb.signupUser(email, password);
            if (signupResult.contains("idToken")) {
                String firestoreResult = fb.addUserToFirestore(universityName, email, name, uniType, signupResult, stateVal, universityName, universityName, password);
                messageLabel.setText("✅ Registered Successfully");
                messageLabel.setTextFill(Color.WHITE);
            } else {
                messageLabel.setText("❌ Registration Failed");
                messageLabel.setTextFill(Color.WHITE);
            }
        });

        form.getChildren().addAll(
                nameField, emailField, field, state, universityType, university, passwordField, registerBtn, messageLabel
        );
        form.setAlignment(Pos.CENTER);

        centerWrapper.getChildren().setAll(form);
    }

    private void showAdminLoginForm() {
        VBox loginBox = createFormWrapper("Admin Login");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");

        loginBtn.setOnAction(e -> {
            String email = usernameField.getText();
            String password = passwordField.getText();

            boolean success = fb.signInWithEmailAndPassword(email, password);
            messageLabel.setText(success ? "✅ SignIn Successful" : "❌ SignIn Failed");

            if (success) {
                try {
                    new landingpage2().start(new Stage());
                    ((Stage) loginBtn.getScene().getWindow()).close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                usernameField.clear();
                passwordField.clear();

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText("Sign In Failed");
                alert.setContentText("Invalid email or password. Please try again.");
                alert.showAndWait();
            }
        });

        loginBox.getChildren().addAll(usernameField, passwordField, loginBtn);
        loginBox.setAlignment(Pos.CENTER);
        centerWrapper.getChildren().setAll(loginBox);
    }

    private void showWelcomeScreen() {
        VBox welcomeBox = new VBox(20);
        welcomeBox.setAlignment(Pos.CENTER);
        // welcomeBox.setPadding(new Insets(30));
        // welcomeBox.setStyle(
        //         "-fx-background-color: Peach;" +
        //                 "-fx-background-radius: 10;" +
        //                 "-fx-border-radius: 10;"
                    
        // );

        Label logo = new Label("Welcome to Smart College Support Portal");
        logo.setFont(Font.font("Arial", 24));

        logo.setTextFill(Color.BLACK);

        Label desc = new Label("Connect with universities, register for courses, and explore career options.");
        desc.setWrapText(true);
        desc.setFont(Font.font("Arial", 20));
        // desc.setTextFill(Color.web("#051220ff"));

        // Image image = new Image(getClass().getResourceAsStream("/images/login2.png"));
        // ImageView imageView = new ImageView(image);
        // imageView.setFitWidth(300);
        // imageView.setPreserveRatio(true);

        welcomeBox.getChildren().addAll(logo,desc);
        centerWrapper.getChildren().setAll(welcomeBox);
        centerWrapper.setAlignment(Pos.CENTER);
    }
}


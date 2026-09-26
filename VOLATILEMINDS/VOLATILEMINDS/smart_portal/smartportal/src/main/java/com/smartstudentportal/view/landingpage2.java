package com.smartstudentportal.view;

import java.util.*;
import java.util.concurrent.ExecutionException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.smartstudentportal.controller.FirebaseConfig;

public class landingpage2 extends Application {
    private VBox contentArea;
    public Firestore db = FirebaseConfig.getFirestoreObject();
    public Map<String, Object> adminData = new HashMap<>();
    List<TextField> textFieldsList = new ArrayList<>();
    String docId = "";

    @Override
    public void start(Stage stage) {
        // Sidebar
        VBox sidebar = new VBox(15);
        sidebar.setPrefWidth(220);
        sidebar.setPadding(new Insets(20));
        sidebar.setOpacity(0.9);
        //sidebar.setStyle("-fx-background-color: #2980b9;");

        Label welcomeLabel = new Label("ADMIN\nDASHBOARD");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        welcomeLabel.setTextFill(Color.WHITE);
        welcomeLabel.setMaxWidth(Double.MAX_VALUE);
        welcomeLabel.setAlignment(Pos.CENTER);
        welcomeLabel.setPadding(new Insets(10, 0, 20, 0));
        welcomeLabel.setStyle("-fx-background-color: #1ABC9C; -fx-background-radius: 8;");

        Image logoImg = new Image(getClass().getResourceAsStream("/images/login2.png"));
        ImageView logoView = new ImageView(logoImg);
        logoView.setFitWidth(60);
        logoView.setFitHeight(60);

        // Sidebar buttons: Home and Logout only
        Button homeBtn = createSidebarButton("Home");
        Button logoutBtn = createSidebarButton("Logout");

        for (Button btn : new Button[]{homeBtn, logoutBtn}) {
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ecf0f1; -fx-font-size: 15px;");
            btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #ecf0f1; -fx-text-fill: #2980b9;"));
            btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ecf0f1; -fx-font-size: 13px;"));
        }

        sidebar.getChildren().addAll(logoView, welcomeLabel, homeBtn, logoutBtn);

        contentArea = new VBox();
        contentArea.setPadding(new Insets(30));
        contentArea.setSpacing(10);
        contentArea.setOpacity(0.9);
        //contentArea.setStyle("-fx-background-color: #ecf0f1;");

        Label welcomeLabel2 = new Label("Welcome to the Admin Dashboard");
        welcomeLabel2.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        welcomeLabel2.setTextFill(Color.BLACK);
        welcomeLabel2.setMaxWidth(Double.MAX_VALUE);
        welcomeLabel2.setAlignment(Pos.CENTER);
        welcomeLabel2.setPadding(new Insets(10, 0, 20, 0));
        welcomeLabel2.setStyle("-fx-border-color:white");
        //welcomeLabel2.setStyle("-fx-background-color: #2980b9; -fx-background-radius: 8;");
        contentArea.getChildren().add(welcomeLabel2);

        // Event handlers
        homeBtn.setOnAction(new HomeHandler());
        logoutBtn.setOnAction(new LogoutHandler(stage));

        BorderPane rootpane = new BorderPane();
        rootpane.setLeft(sidebar);
        rootpane.setCenter(contentArea);
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
    dimLayer.prefWidthProperty().bind(rootpane.widthProperty());
    dimLayer.prefHeightProperty().bind(rootpane.heightProperty());

    StackPane stack = new StackPane(bgPane, dimLayer, rootpane);


        Scene scene = new Scene(stack, 1300, 700);
        stage.setScene(scene);
        stage.setTitle("Admin Dashboard");
        stage.show();
    }

    private Button createSidebarButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-size: 14px;");
        return btn;
    }

    // Handler for Home: show all three sections vertically stacked inside scrollpane
    private class HomeHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            contentArea.getChildren().clear();

            Label heading = new Label("Manage Website Content");
            heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
            heading.setPadding(new Insets(0, 0, 20, 0));

            VBox mainContainer = new VBox(40);
            mainContainer.setPadding(new Insets(20));
            mainContainer.setPrefWidth(1150);
            mainContainer.setOpacity(0.9);

            // // === College Features ===
            VBox collegeSection = new VBox(15);
            Label collegeTitle = new Label("College Details");
            collegeTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2980b9;");

            VBox collegeList = new VBox(20);
            collegeList.setPadding(new Insets(10));
            collegeList.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-border-radius: 8;");
            
            // Form to add new college
            TextField collegeNameInput = new TextField();
            collegeNameInput.setPromptText("College Name");
            TextArea collegeDescInput = new TextArea();
            collegeDescInput.setPromptText("College Description");
            collegeDescInput.setPrefRowCount(2);
            TextArea collegeFeaturesInput = new TextArea();
            collegeFeaturesInput.setPromptText("Features (comma-separated)");
            collegeFeaturesInput.setPrefRowCount(2);
            Button addCollegeBtn = new Button("Add College");
            addCollegeBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
            addCollegeBtn.setOnAction(e -> {
                String name = collegeNameInput.getText().trim();
                String desc = collegeDescInput.getText().trim();
                String[] features = collegeFeaturesInput.getText().split(",");
                if(name.isEmpty() || desc.isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Please enter College Name and Description").showAndWait();
                    return;
                }
                adminData.put("name", name);
                adminData.put("desc", desc);
                adminData.put("featuredata", String.join(",", features));
                ApiFuture<DocumentReference> docRef =  db.collection("college").add(adminData);
                try {
                    docId= docRef.get().getId();
                    System.out.println(docId);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (ExecutionException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                addEditableCollegeCard(collegeList, name, desc, features);
                collegeNameInput.clear();
                collegeDescInput.clear();
                collegeFeaturesInput.clear();
            });
            VBox addCollegeBox = new VBox(10, new Label("Add New College"), collegeNameInput, collegeDescInput, collegeFeaturesInput, addCollegeBtn);
            addCollegeBox.setPadding(new Insets(15));
            addCollegeBox.setStyle("-fx-background-color: #f5f6fa; -fx-border-color: #3498db; -fx-border-radius: 8;");

            collegeSection.getChildren().addAll(collegeTitle, collegeList, addCollegeBox);

            // === College Features ===
            VBox collegeSection1 = new VBox(15);
            Label collegeTitle2 = new Label("College Features");
            collegeTitle2.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2980b9;");

            VBox collegeList1 = new VBox(20);
            collegeList1.setPadding(new Insets(10));
            collegeList1.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-border-radius: 8;");
            
            // Form to add new college
            TextField collegeNameInput1 = new TextField();
            collegeNameInput1.setPromptText("Administrative support");
            collegeNameInput1.setPrefHeight(200);
            collegeNameInput1.setAlignment(Pos.TOP_LEFT);

            TextArea collegeDescInput2= new TextArea();
            collegeDescInput2.setPromptText("College Facilities");
            collegeDescInput2.setPrefRowCount(2);
            collegeDescInput2.setPrefHeight(200);
            
            Button addCollegeBtn2 = new Button("Add College");
            addCollegeBtn2.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
            addCollegeBtn2.setOnAction(e -> {
                String name = collegeNameInput1.getText().trim();
                String desc = collegeDescInput2.getText().trim();
                if(name.isEmpty() || desc.isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Please enter College Name and Description").showAndWait();
                    return;
                }
                adminData.put("administrative_support", name);
                adminData.put("college_facilities", desc);
                System.out.println("-------------------------------------Document ID : "+docId);
                try{
                    System.out.println("In try");
                db.collection("college").document(docId).update(adminData);
                System.out.println("After try");
                }catch(Exception ep){
                    System.out.println("In catch");
                    ep.printStackTrace();
                }
                addEditableCollegeCard(collegeList, name, desc);
                collegeNameInput1.clear();
                collegeDescInput2.clear();
            });
            VBox addCollegeBox2 = new VBox(10, new Label("Add New College"), collegeNameInput1, collegeDescInput2, addCollegeBtn2);
            addCollegeBox2.setPadding(new Insets(15));
            addCollegeBox2.setStyle("-fx-background-color: #f5f6fa; -fx-border-color: #3498db; -fx-border-radius: 8;");

            collegeSection1.getChildren().addAll(collegeTitle2, collegeList1, addCollegeBox2);

            // === Admission Process ===
            VBox admissionSection = new VBox(15);
            Label admissionTitle = new Label("Admission Process");
            admissionTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2980b9;");

            VBox admissionContent = new VBox(25);
            admissionContent.setPadding(new Insets(10));
            admissionContent.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-border-radius: 8;");

            admissionContent.getChildren().addAll(
                    createEditableSection("CAP Rounds (Centralized Admission Process)", new String[]{
                            "CAP Round 1: Choice filling and allotment based on rank.",
                            "CAP Round 2: Upgradation based on available seats.",
                            "CAP Round 3: Institute-level spot round if seats remain vacant.",
                            "Reporting: Candidates must report physically to confirm admission."
                    }),
                    createEditableSection("Documents Required for Admission", new String[]{
                            "10th & 12th Marksheet and Passing Certificate",
                            "Entrance Exam Scorecard (JEE/CET etc.)",
                            "Domicile Certificate / Nationality Certificate",
                            "Caste Certificate (if applicable)",
                            "Income Certificate for scholarship (if applicable)",
                            "Passport-size Photos, Aadhar Card (ID Proof)"
                    }),
                    createEditableSection("Past Year Cutoff Examples (General Category)", new String[]{
                            
                    }),
                    createEditableSection("Branch-Wise Intake (Sample Colleges)", new String[]{
                            
                    })
            );

            admissionSection.getChildren().addAll(admissionTitle, admissionContent);

            // === Branch Selection ===
            VBox branchSection = new VBox(15);
            Label branchTitle = new Label("Engineering Branches");
            branchTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2980b9;");

            VBox branchListVBox = new VBox(20);
            branchListVBox.setPadding(new Insets(10));
            branchListVBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-border-radius: 8;");

            // Predefined branches
            branchListVBox.getChildren().addAll(
                createEditableBranchCard("Computer Science Engineering (CSE)",
                    "",
                    "",
                    new String[]{"Google", "Amazon", "Infosys", "TCS", "Microsoft"}
                ),
                createEditableBranchCard("Electronics and Telecommunication Engineering (ENTC)",
                    "",
                    "",
                    new String[]{"Qualcomm", "Intel", "Reliance Jio", "Cognizant"}
                )
            );

            Button addBranchBtn = new Button("+ Add New Branch");
            addBranchBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
            addBranchBtn.setOnAction(e -> {
                branchListVBox.getChildren().add(createEditableBranchCard("", "", "", new String[0]));
            });

            branchSection.getChildren().addAll(branchTitle, branchListVBox, addBranchBtn);

            // ===== Add all sections to main container =====
            mainContainer.getChildren().addAll(collegeSection, collegeSection1,admissionSection, branchSection);

            ScrollPane scrollPane = new ScrollPane(mainContainer);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color: transparent;");
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            VBox wrapper = new VBox(heading, scrollPane);
            wrapper.setPadding(new Insets(10));
            contentArea.getChildren().add(wrapper);
        }

        // ––––– Helpers –––––

        private void addEditableCollegeCard(VBox collegeList, String administrative_support, String college_facilities) {
            TextField nameField = new TextField(administrative_support);
            nameField.setStyle("-fx-font-weight: bold;");
            

            TextArea descArea = new TextArea(college_facilities);
            descArea.setWrapText(true);
            descArea.setPrefRowCount(2);
            Button deleteBtn = new Button("Delete");
            deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
            // deleteBtn.setOnAction(e -> container.getChildren().remove(nameField.getParent()));

            HBox btnBox = new HBox(10, deleteBtn);
            btnBox.setAlignment(Pos.CENTER_RIGHT);

            VBox card = new VBox(10,
                    new Label("College Name:"), nameField,
                    new Label("Description:"), descArea,
                    btnBox
            );
            card.setPadding(new Insets(15));
            card.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #2980b9; -fx-border-radius: 8;");
            // container.getChildren().add(card);
        }
    

        private void addEditableCollegeCard(VBox container, String collegeName, String description, String[] features) {
            TextField nameField = new TextField(collegeName);
            nameField.setStyle("-fx-font-weight: bold;");

            TextArea descArea = new TextArea(description);
            descArea.setWrapText(true);
            descArea.setPrefRowCount(2);

            TextArea featuresArea = new TextArea(String.join(", ", features));
            featuresArea.setWrapText(true);
            featuresArea.setPrefRowCount(2);

            

            Button deleteBtn = new Button("Delete");
            deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
            deleteBtn.setOnAction(e -> container.getChildren().remove(nameField.getParent()));

            HBox btnBox = new HBox(10, deleteBtn);
            btnBox.setAlignment(Pos.CENTER_RIGHT);
            

            VBox card = new VBox(10,
                    new Label("College Name:"), nameField,
                    new Label("Description:"), descArea,
                    new Label("Features:"), featuresArea,
                    btnBox
            );
            card.setPadding(new Insets(15));
            card.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #2980b9; -fx-border-radius: 8;");
            container.getChildren().add(card);
        }

        private VBox createEditableSection(String title, String[] initialPoints) {
            Label sectionTitle = new Label(title);
            sectionTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2980b9;");

            VBox fieldsBox = new VBox(10);
            List<TextField> fieldList = new ArrayList<>();

            for (String point : initialPoints) {
                TextField tf = new TextField(point);
                tf.setStyle("-fx-font-size: 14px;");
                fieldList.add(tf);
                fieldsBox.getChildren().add(tf);
            }

            Button addLineBtn = new Button("+ Add Line");
            addLineBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
            addLineBtn.setOnAction(e -> {
                TextField tf = new TextField();
                tf.setPromptText("Enter new item...");
                tf.setStyle("-fx-font-size: 14px;");
                fieldList.add(tf);
                fieldsBox.getChildren().add(tf);
            });

            Button saveBtn = new Button("Save Section");
            saveBtn.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");
            saveBtn.setOnAction(e -> {
                System.out.println("Saved content for: " + title);
                List<String> dataList = new ArrayList<>();
                for (TextField tf : fieldList) {
                    dataList.add(tf.getText());
                    System.out.println("- " + tf.getText());
                }
                 adminData.put(title,dataList);
                 System.out.println(adminData);
                 db.collection("college").document(docId).update(adminData);
                new Alert(Alert.AlertType.INFORMATION,
                        "Changes saved for section: " + title).showAndWait();
            });

            HBox buttons = new HBox(10, addLineBtn, saveBtn);
            buttons.setAlignment(Pos.CENTER_RIGHT);

            VBox section = new VBox(10, sectionTitle, fieldsBox, buttons);
            section.setPadding(new Insets(15));
            section.setStyle("-fx-background-color: #f8f9fa; -fx-border-radius: 8; -fx-border-color: #ddd;");
            return section;
        }

        private VBox createEditableBranchCard(String branchName, String description, String careers, String[] recruiters) {
            TextField nameField = new TextField(branchName);
            nameField.setPromptText("Branch Name");
            nameField.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            TextArea descArea = new TextArea(description);
            descArea.setPromptText("Branch Description");
            descArea.setWrapText(true);

            TextArea careerArea = new TextArea(careers);
            careerArea.setPromptText("Career Opportunities");
            careerArea.setWrapText(true);

            Label recruiterLabel = new Label("Top Recruiters:");
            recruiterLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

            VBox recruitersBox = new VBox(5,careerArea ,descArea,recruiterLabel);
            List<TextField> recruiterFields = new ArrayList<>();
            
            for (String rec : recruiters) {
                TextField tf = new TextField(rec);
                recruiterFields.add(tf);
                recruitersBox.getChildren().add(tf);
            }

            Button addRecBtn = new Button("+ Add Recruiter");
            addRecBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
            addRecBtn.setOnAction(e -> {
                TextField tf = new TextField();
                tf.setPromptText("Recruiter Name");
                recruiterFields.add(tf);
                recruitersBox.getChildren().add(tf);
            });
            recruitersBox.getChildren().add(addRecBtn);

            Button saveBtn = new Button("Save Branch");
            saveBtn.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");
            saveBtn.setOnAction(e -> {
                System.out.println("Branch: " + nameField.getText());
                System.out.println("Description: " + descArea.getText());
                System.out.println("Careers: " + careerArea.getText());
                System.out.println("Recruiters:");
                List<String> dataList = new ArrayList<>();
                for (TextField tf : recruiterFields) {
                    dataList.add(tf.getText());
                    System.out.println("- " + tf.getText());
                }
                adminData.put("branchName", nameField.getText());
                adminData.put("descArea", descArea.getText());
                adminData.put("careerArea", careerArea.getText());
                adminData.put(branchName,dataList);
                 db.collection("college").document(docId).update(adminData);
                new Alert(Alert.AlertType.INFORMATION,
                        "Branch \"" + nameField.getText() + "\" saved!").showAndWait();
            });

            Button deleteBtn = new Button("Delete Branch");
            deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
            deleteBtn.setOnAction(e -> {
                ((VBox) deleteBtn.getParent().getParent()).getChildren().remove(deleteBtn.getParent());
            });

            HBox buttons = new HBox(10, saveBtn, deleteBtn);
            buttons.setAlignment(Pos.CENTER_RIGHT);

            VBox branchCard = new VBox(10,
                    nameField,
                    descArea,
                    careerArea,
                    recruitersBox,
                    buttons
            );
            branchCard.setPadding(new Insets(15));
            branchCard.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #2980b9; -fx-border-radius: 8;");
            return branchCard;
        }
        
    }

    private class LogoutHandler implements EventHandler<ActionEvent> {
        private Stage currentStage;

        public LogoutHandler(Stage stage) {
            this.currentStage = stage;
        }

        @Override
        public void handle(ActionEvent event) {
            new mainscreen().start(new Stage());
            currentStage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


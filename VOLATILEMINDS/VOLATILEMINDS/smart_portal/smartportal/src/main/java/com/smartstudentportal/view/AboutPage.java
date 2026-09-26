package com.smartstudentportal.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AboutPage extends Application{

    
    public Parent createScene(Runnable back) {
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: black; -fx-background-radius: 15; -fx-background-color: #A5D6A7;");
        backButton.setOnAction(e -> back.run());

        // Title and project name
        Text title = new Text("About Us");
        title.setFont(Font.font("Arial", 20));
        title.setFill(Color.BLACK);

        HBox hb = new HBox(20, backButton, title);
        hb.setAlignment(Pos.CENTER_LEFT);

        Text projectName = new Text("SMART COLLEGE SUPPORT PORTAL");
        projectName.setFont(Font.font("Arial", 24));
        projectName.setFill(Color.BLACK);

        
        ImageView image1=new ImageView("images\\login2.png");
        image1.setFitWidth(250);
        image1.setPreserveRatio(true);
        VBox vbox2 = new VBox(image1);
        vbox2.setAlignment(Pos.CENTER);
        // Project description
        Text description = new Text(
                "Welcome to Smart College Support Portal, you are one step closer to feasible access to all the Admission process information on a single platform\n" +
                "Our mission is to help the students in a way where they dont have to consult any counsellor and can get all the required details about the admission process,documentation required,branch selection,etc on a single click\n\n" +
                "We are here to give all the required support not only for particular college,but a hub where they can reach to multiple college information faster\n\n" +
                "Whether you're a student looking for any of the difficulty from the date of admission to the day you enter in your dream college,we are here to help you!!\n\n" +
                "THANK YOU!!!"
        );
        description.setFont(Font.font("Arial", 16));
        description.setWrappingWidth(500);

        // Team section
        Text teamLeader = new Text("Team Leader");
        teamLeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Text leaderName = new Text("AYUSH HULE");
        leaderName.setStyle("-fx-font-size: 16px;");

        Text groupMembers = new Text("Group Members");
        groupMembers.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Text member1 = new Text("SIDDHI CHIKHALE");
        Text member2 = new Text("SHREYAS GAWARI");
        member1.setStyle("-fx-font-size: 16px;");
        member2.setStyle("-fx-font-size: 16px;");

        VBox members = new VBox(10, teamLeader, leaderName, groupMembers, member1, member2);
        members.setAlignment(Pos.TOP_LEFT);

        // Left side (Project info, team)
        VBox leftBox = new VBox(20, hb,projectName,vbox2,description, members);
        leftBox.setAlignment(Pos.TOP_LEFT);
        leftBox.setPadding(new Insets(20));
        leftBox.setStyle("-fx-background-color: #e7c0e2ff; -fx-background-radius: 10;");
        leftBox.setPrefHeight(800);

        // Acknowledgments section
        Text c2wTitle = new Text("Core2web");
        c2wTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: #90bde9ff;");
            Text ackText = new Text(
            "On behalf of the entire Core2Web family, I extend my heartfelt gratitude to each and every one of you who has been a part of this wonderful journey.\n\n" +
            "First and foremost,I would like to than our beloved Shashi Bagal Sir who has given the spirit to work on the real time based Projects "+
            "I would like to thank our incredible mentors and instructors whose dedication, clarity, and passion have transformed complex lines of code into valuable knowledge. Your guidance has been the backbone of our learning experience.\n\n" +
            "To my fellow learners – thank you for your enthusiasm, curiosity, and willingness to collaborate. Whether debugging errors or celebrating successful runs, every moment has been enriching and inspiring.\n\n" +
            "A big thank you to the Core2Web team for providing a well-structured curriculum, interactive sessions, and real-world exposure that helped us not just learn programming, but understand how to think like developers.\n\n" +
            "Last but not least, let's carry forward the spirit of Core2Web — to learn, build, and grow together. This may be the end of our session, but it's just the beginning of our journey as future tech creators.\n\n" +
            "Thank you all, and happy coding! 🚀💻"
        );
        ackText.setFont(Font.font("Arial", 16));
        ackText.setWrappingWidth(500);

        ImageView image2=new ImageView("/images/ShashiSir.png");
        image2.setFitWidth(250);
        image2.setPreserveRatio(true);
        VBox vbox1 = new VBox(image2);
        vbox1.setAlignment(Pos.CENTER);

        // Right side (Acknowledgments)
        VBox rightBox = new VBox(20, c2wTitle, vbox1,ackText);
        rightBox.setAlignment(Pos.TOP_CENTER);
        rightBox.setPadding(new Insets(20));
        rightBox.setStyle("-fx-background-color: Wheat; -fx-background-radius: 10;");
        rightBox.setPrefHeight(800);

        // Combine left and right
        HBox mainBox = new HBox(leftBox, rightBox);
        mainBox.setPadding(new Insets(10));

        // Scrollable layout
        ScrollPane scrollPane = new ScrollPane(mainBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: white;");

        return scrollPane;
    }

    @Override
    public void start(Stage arg0) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }
}


// package com.agro_ease;

// import javafx.application.Application;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.image.Image;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.VBox;
// import javafx.scene.paint.Color;
// import javafx.scene.text.Font;
// import javafx.stage.Stage;


// import javafx.scene.image.ImageView;



// import javafx.scene.text.Text;

// public class AboutPage {
//     Stage aboutStage;
//     Scene aboutScene;
//     public void setAboutStage(Stage aboutStage) {
//         this.aboutStage = aboutStage;
//     }
//     public void setAboutScene(Scene aboutScene) {
//         this.aboutScene = aboutScene;
//     }
//     public Parent createScene(Runnable back) {
        



   

//         Button backButton=new Button("Back");
//         backButton.setStyle("-fx-max-width:80; -fx-max-height:50; -fx-font-size:12; -fx-text-color:black; -fx-font-weight:bold");
//         backButton.setOnAction(e ->{
//             System.out.println("click on back button");
//             back.run();
//         });
//         // Title and project name
//         Text title = new Text("About Us");
//         title.setFont(Font.font("Arial", 20));
//         title.setFill(Color.BLACK);

//         HBox hb=new HBox(20,backButton,title);
//         Text projectname = new Text("AgroEase - Farming Made Easy");
//         projectname.setFont(Font.font("Arial", 24));
//         projectname.setFill(Color.BLACK);

//         // Project image
//         ImageView imageView = new ImageView(
//             new Image("assets/Images/agroeaseLogo.jpg")
//         );
//         imageView.setFitWidth(250);
//         imageView.setPreserveRatio(true);

//         // Project description
//         Text tx1 = new Text("Welcome to AgroEase, your one-stop smart farming companion!\n" +
//                 "Our mission is to empower farmers by bringing agriculture, technology, and convenience together in one easy-to-use real time application.\n\n" +
//                 "AgroEase helps farmers order crop seeds, rent agricultural vehicles, track weather updates, and read useful farming articles — all in their own language. With features like voice assistance, secure payments, and real-time booking, we aim to make farming smarter, faster, and more accessible.\n\n" +
//                 "Whether you're a farmer looking for quality products, or a service provider offering tractors and tools, AgroEase brings everything you need to your fingertips.");
//         tx1.setFont(Font.font("Arial", 16));
//         tx1.setWrappingWidth(450);

//         Text teamLeader=new Text("Team Leader");
//         teamLeader.setStyle("-fx-font-size:15; -fx-font-weight:bold");
//         Text teamLeadername=new Text("Siddhi Kharade");
//         teamLeadername.setStyle("-fx-font-size:15");
//         Text grpmember=new Text("Group Members");
//         grpmember.setStyle("-fx-font-size:15; -fx-font-weight:bold");
//         Text grpmember1=new Text("Arati Phalke");
//         grpmember1.setStyle("-fx-font-size:15");
//         Text grpmember2=new Text("Sakshi Shende");
//         grpmember2.setStyle("-fx-font-size:15");
//         Text grpmember3=new Text("Vedant Dixit");
//         grpmember3.setStyle("-fx-font-size:15");

//         VBox members=new VBox(10,teamLeader,teamLeadername,grpmember,grpmember1,grpmember2,grpmember3);
//         members.setAlignment(Pos.BOTTOM_RIGHT);

//         // Left side VBox
//         VBox leftBox = new VBox(20, hb, projectname, imageView, tx1,members);
//         leftBox.setAlignment(Pos.TOP_LEFT);
//         leftBox.setPadding(new Insets(20));
//         leftBox.setStyle("-fx-background-color: #66BB6A;");
//         leftBox.setPrefHeight(800);
//         // Rectangle clip=new Rectangle();
//         // clip.setArcWidth(60);
//         // clip.setArcHeight(50);
//         // clip.widthProperty().bind(leftBox.widthProperty());
//         // clip.heightProperty().bind(leftBox.heightProperty());
//         // leftBox.setClip(clip);

//         Text c2wname= new Text("Core2web");
//         c2wname.setStyle("-fx-font-size:24; -fx-font-Weight:bold; -fx-text-color:black");
//         VBox c2wbBox= new VBox(c2wname);
//         c2wbBox.setAlignment(Pos.CENTER);
//         // Core2Web image and thank-you message
//         ImageView c2w = new ImageView(
//             new Image("assets\\Images\\c2w.jpg")
//         );
//         c2w.setFitWidth(150);
//         c2w.setPreserveRatio(true);
//        // c2w.setAlignment(Pos.CENTER);
//        VBox vbox = new VBox(c2w);
//        vbox.setAlignment(Pos.CENTER);

//         Text tx2 = new Text("On behalf of our entire project team, we would like to express our sincere gratitude to Core2web for their invaluable guidance and support throughout the development of our project \"AgroEase\".\n\n" +
//                 "Their constant motivation, technical assistance, and mentorship played a crucial role in helping us transform our idea into a fully functional application. From clarifying our doubts to encouraging us to explore new technologies, the team at Core2web has been an incredible pillar of support.\n\n" +
//                 "Thank you once again for your dedication and mentorship, we truly appreciate your contribution to our learning journey.");
//         tx2.setFont(Font.font("Arial", 14));
//         tx2.setWrappingWidth(450);

//         ImageView image2=new ImageView("assets/Images/ss.jpg");
//         image2.setFitWidth(250);
//         image2.setPreserveRatio(true);
//        VBox vbox1 = new VBox(image2);
//        vbox1.setAlignment(Pos.CENTER);

//         VBox rightBox = new VBox(20, c2wbBox,vbox, tx2,vbox1);
//         rightBox.setAlignment(Pos.TOP_RIGHT);
//         rightBox.setPadding(new Insets(20));
//         rightBox.setPrefHeight(800);
//         rightBox.setStyle("-fx-background-color: white;");

//         // Combine both in HBox
//         HBox mainBox = new HBox(leftBox, rightBox);
//         mainBox.setPadding(new Insets(10));

//         // Scrollable layout
//         ScrollPane scrollPane = new ScrollPane(mainBox);
//         scrollPane.setFitToWidth(true);
//         scrollPane.setStyle("-fx-background-color: white;");

//         // Scene scene = new Scene(scrollPane, 1000, 800);
//        return scrollPane;
//     }

// }
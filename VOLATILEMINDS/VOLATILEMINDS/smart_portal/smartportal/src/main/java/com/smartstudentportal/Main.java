package com.smartstudentportal;

import com.smartstudentportal.controller.FirebaseConfig;
import com.smartstudentportal.view.mainscreen;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        new FirebaseConfig();
      Application.launch(mainscreen.class);
      

    }
}
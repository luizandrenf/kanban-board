package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.infra.screens.SignInView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        SignInView loginView = new SignInView();
        loginView.start(primaryStage);  // Inicia a aplicação pela tela de login
    }

    public static void main(String[] args) {
        launch(args);  // Lança a aplicação JavaFX
    }
}

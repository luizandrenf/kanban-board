package org.example.infra.screens;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.domain.application.services.UserService;
import org.example.infra.DB.repositories.HibernateUserRepository;

public class SignUpView extends Application {

    private UserService userService = new UserService(new HibernateUserRepository());

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sign Up");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);

        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);

        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 1);

        Button createAccountButton = new Button("Create Account");
        GridPane.setConstraints(createAccountButton, 1, 2);

        Label messageLabel = new Label();
        GridPane.setConstraints(messageLabel, 1, 3);

        createAccountButton.setOnAction(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            boolean success = userService.createAccount(username, password);
            if (success) {
                new SignInView().start(primaryStage); // Redireciona de volta para a tela de login
            } else {
                messageLabel.setText("Failed to create account. Try again.");
            }
        });

        grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, createAccountButton, messageLabel);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

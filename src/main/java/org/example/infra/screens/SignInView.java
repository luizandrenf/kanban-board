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
import org.example.domain.application.services.TaskService;
import org.example.domain.application.services.UserService;
import org.example.domain.enterprise.entities.User;
import org.example.infra.DB.repositories.HibernateTaskRepository;
import org.example.infra.DB.repositories.HibernateUserRepository;

import java.util.Optional;

public class SignInView extends Application {

    private UserService userService = new UserService(new HibernateUserRepository());

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sign In");

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

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);

        Button signUpButton = new Button("Create Account");
        GridPane.setConstraints(signUpButton, 1, 3);

        Label messageLabel = new Label();
        GridPane.setConstraints(messageLabel, 1, 4);

        loginButton.setOnAction(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            Optional<User> user = userService.authenticate(username, password);


            if (user.isPresent() && user.get().getPassword().equals(password)) {
                new KanbanBoardView(new TaskService(new HibernateTaskRepository()), user.get()).start(primaryStage);

            } else {
                messageLabel.setText("Invalid credentials!");
            }
        });

        signUpButton.setOnAction(e -> {
            new SignUpView().start(primaryStage);
        });

        grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton, signUpButton, messageLabel);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

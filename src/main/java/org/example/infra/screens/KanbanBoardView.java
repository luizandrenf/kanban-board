package org.example.infra.screens;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.example.domain.application.services.TaskService;
import org.example.domain.enterprise.entities.Task;
import org.example.domain.enterprise.entities.WorkTask;
import org.example.domain.enterprise.entities.PersonalTask;
import org.example.domain.enterprise.entities.User;
import org.example.domain.enterprise.enums.Status;

import java.util.Optional;

public class KanbanBoardView extends Application {

    private TaskService taskService;
    private User currentUser;
    private VBox toDoColumn;
    private VBox doingColumn;
    private VBox doneColumn;

    public KanbanBoardView(TaskService taskService, User currentUser) {
        this.taskService = taskService;
        this.currentUser = currentUser;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Kanban Board");

        // Create columns
        toDoColumn = createColumn("To Do");
        doingColumn = createColumn("Doing");
        doneColumn = createColumn("Done");

        // Layout for Kanban columns
        HBox kanbanLayout = new HBox(10, toDoColumn, doingColumn, doneColumn);
        kanbanLayout.setPadding(new Insets(10));

        // Center the Kanban board
        kanbanLayout.setAlignment(Pos.CENTER);

        // Use BorderPane for layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));
        mainLayout.setCenter(kanbanLayout);

        // Layout for adding tasks
        VBox addTaskLayout = createAddTaskLayout();
        mainLayout.setTop(addTaskLayout);

        // Update columns with tasks
        updateColumns();

        Scene scene = new Scene(mainLayout, 1200, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createColumn(String title) {
        VBox column = new VBox(10);
        column.setPadding(new Insets(10));
        column.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-background-radius: 5;");

        //
        switch (title) {
            case "To Do":
                column.setStyle(column.getStyle() + "-fx-background-color: #f8d7da;"); // Color for "To Do"
                break;
            case "Doing":
                column.setStyle(column.getStyle() + "-fx-background-color: #fff3cd;"); // Color for "Doing"
                break;
            case "Done":
                column.setStyle(column.getStyle() + "-fx-background-color: #d4edda;"); // Color for "Done"
                break;
        }

        column.setMinWidth(300);
        column.setPrefWidth(300);
        column.setMinHeight(600);
        column.setPrefHeight(600);

        Label label = new Label(title);
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        column.getChildren().add(label);

        // Enable drop for columns
        column.setOnDragOver(event -> {
            if (event.getGestureSource() != column && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        column.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                String taskId = db.getString();
                Optional<Task> taskOptional = taskService.findById(Long.parseLong(taskId));
                if (taskOptional.isPresent()) {
                    Task task = taskOptional.get();
                    Status newStatus = getColumnStatus(column);
                    task.setStatus(newStatus);
                    taskService.update(task);
                    updateColumns();
                    success = true;
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

        return column;
    }

    private VBox createAddTaskLayout() {
        // Labels
        Label titleLabel = new Label("Title:");
        Label descriptionLabel = new Label("Description:");
        Label placeOrDepartmentLabel = new Label("Place/Department:");
        Label typeLabel = new Label("Type:");

        // Inputs
        TextField titleInput = new TextField();
        TextField descriptionInput = new TextField();
        TextField placeOrDepartmentInput = new TextField();
        ComboBox<String> typeComboBox = new ComboBox<>(FXCollections.observableArrayList("Work", "Personal"));

        // Styling inputs and labels
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        descriptionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        placeOrDepartmentLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        typeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        titleInput.setPromptText("Enter task title");
        descriptionInput.setPromptText("Enter task description");
        placeOrDepartmentInput.setPromptText("Enter place or department");
        typeComboBox.setPromptText("Select task type");

        // Adjust sizes
        titleInput.setPrefWidth(400);
        descriptionInput.setPrefWidth(400);
        placeOrDepartmentInput.setPrefWidth(400);
        typeComboBox.setPrefWidth(400);

        // Button
        Button addButton = new Button("Add Task");
        addButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10; -fx-font-size: 14px;");

        addButton.setOnAction(e -> {
            String title = titleInput.getText();
            String description = descriptionInput.getText();
            String placeOrDepartment = placeOrDepartmentInput.getText();
            String type = typeComboBox.getValue();

            if (title.isEmpty() || description.isEmpty() || placeOrDepartment.isEmpty() || type == null) {
                showAlert("Input Error", "Please fill in all fields and select a type.");
                return;
            }

            Task newTask;
            if (type.equals("Work")) {
                newTask = new WorkTask();
                ((WorkTask) newTask).setDepartment(placeOrDepartment);
            } else {
                newTask = new PersonalTask();
                ((PersonalTask) newTask).setPlace(placeOrDepartment);
            }
            newTask.setTitle(title);
            newTask.setDescription(description);
            newTask.setStatus(Status.TODO);
            newTask.setUser(currentUser);
            taskService.addTask(newTask);

            titleInput.clear();
            descriptionInput.clear();
            placeOrDepartmentInput.clear();
            typeComboBox.setValue(null);

            updateColumns();
        });

        // Layout
        GridPane formLayout = new GridPane();
        formLayout.setHgap(10);
        formLayout.setVgap(10);
        formLayout.setPadding(new Insets(15));

        formLayout.add(titleLabel, 0, 0);
        formLayout.add(titleInput, 1, 0);
        formLayout.add(descriptionLabel, 0, 1);
        formLayout.add(descriptionInput, 1, 1);
        formLayout.add(placeOrDepartmentLabel, 0, 2);
        formLayout.add(placeOrDepartmentInput, 1, 2);
        formLayout.add(typeLabel, 0, 3);
        formLayout.add(typeComboBox, 1, 3);
        formLayout.add(addButton, 1, 4);

        VBox addTaskLayout = new VBox(15, formLayout);
        addTaskLayout.setPadding(new Insets(20));
        addTaskLayout.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: #e3f2fd; -fx-border-radius: 5; -fx-background-radius: 5;");
        addTaskLayout.setPrefWidth(450);

        return addTaskLayout;
    }


    private void updateColumns() {
        toDoColumn.getChildren().setAll(createColumn("To Do").getChildren());
        doingColumn.getChildren().setAll(createColumn("Doing").getChildren());
        doneColumn.getChildren().setAll(createColumn("Done").getChildren());

        addTasksToColumn(toDoColumn, Status.TODO);
        addTasksToColumn(doingColumn, Status.DOING);
        addTasksToColumn(doneColumn, Status.DONE);
    }

    private void addTasksToColumn(VBox column, Status status) {
        ObservableList<Task> tasks = FXCollections.observableArrayList(taskService.findManyByStatusAndUser(status.name(), currentUser));
        for (Task task : tasks) {
            Label taskLabel = new Label(task.getTitle());
            taskLabel.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-padding: 5; -fx-cursor: hand;");

            // Drag event
            taskLabel.setOnDragDetected(e -> {
                Dragboard db = taskLabel.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(task.getId().toString());
                db.setContent(content);
                e.consume();
            });

            // Click event to show task details
            taskLabel.setOnMouseClicked(e -> showTaskDetails(task));

            column.getChildren().add(taskLabel);
        }
    }

    private Status getColumnStatus(VBox column) {
        Label label = (Label) column.getChildren().get(0);
        switch (label.getText()) {
            case "To Do":
                return Status.TODO;
            case "Doing":
                return Status.DOING;
            case "Done":
                return Status.DONE;
            default:
                return null;
        }
    }

    private void showTaskDetails(Task task) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Task Details");
        dialog.setHeaderText(task.getTitle());

        String details = "Description: " + task.getDescription() + "\n" +
                "Status: " + task.getStatus() + "\n";

        if (task instanceof WorkTask) {
            details += "Department: " + ((WorkTask) task).getDepartment();
        } else if (task instanceof PersonalTask) {
            details += "Place: " + ((PersonalTask) task).getPlace();
        }

        Label contentLabel = new Label(details);
        dialog.getDialogPane().setContent(contentLabel);

        ButtonType deleteButtonType = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(deleteButtonType, cancelButtonType);

        // Apply red style to the delete button
        Button deleteButton = (Button) dialog.getDialogPane().lookupButton(deleteButtonType);
        deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        // Show the dialog and wait for a response
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == deleteButtonType) {
            taskService.delete(task.getId());
            updateColumns();
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

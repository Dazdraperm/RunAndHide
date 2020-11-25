package kpfu.itis.group907.Game.Play;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class WindowMain extends Application {

    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/start.fxml"));

        Scene scene = new Scene(root, 1000, 720);
        primaryStage.setTitle("My first JavaFx app");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    // key press processing
//        scene.setOnKeyPressed(key -> {
//            switch (key.getCode()) {
//                case Q:
//                    primaryStage.close();
//            }
//        });
    private Button alert() {
        //alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        Button cl = new Button("click me");
        cl.setOnAction(event -> alert.show());
        return cl;
    }

    private void showDialog() {
        // dialog window
        Dialog<String> dialog = new Dialog();
        dialog.setTitle("Some form");

        // grid fot input
        GridPane grid = new GridPane();
        final ButtonType sendButtonType = new ButtonType("Send", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(sendButtonType);
        final TextField username = new TextField();
        username.setPromptText("Username");
        grid.add(username, 1, 0);
        dialog.getDialogPane().setContent(grid);

        // button press processing
        dialog.setResultConverter(
                button -> {
                    if (button.equals(sendButtonType)) {
                        System.out.println(username.getText());
                        return username.getText();
                    }
                    return null;
                }
        );

        // processing input
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(System.out::println);
    }

    // run app
    public static void main(String[] args) {
        launch(args);
    }
}
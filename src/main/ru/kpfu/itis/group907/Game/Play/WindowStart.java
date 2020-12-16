package kpfu.itis.group907.Game.Play;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kpfu.itis.group907.Game.Play.readyGame.WaitGame;
import kpfu.itis.group907.Game.Server.udp.Client.Client;

import java.io.IOException;

public class WindowStart {

    @FXML
    private TextField nameFiled;

    @FXML
    private TextField colorField;

    @FXML
    private Button goServer;

    @FXML
    void initialize() {
        goServer.setOnAction(event -> {
            String name = nameFiled.getText();
            Client client;

            if (!nameFiled.getText().equals("")) {

                try {

                    goServer.getScene().getWindow().hide();


                    WaitGame waitGame = new WaitGame();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/waitGame.fxml"));

                    loader.setController(waitGame);

                    Pane mainPane = loader.load();

                    Scene scene = new Scene(mainPane, 1000, 720);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    client = new Client(name, waitGame);
                    client.start();
                    stage.showAndWait();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
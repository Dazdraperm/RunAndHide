package kpfu.itis.group907.Game.Play;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kpfu.itis.group907.Game.Server.udp.Client;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

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
                    client = new Client(name);
                    System.out.println(client);
                    goServer.getScene().getWindow().hide();

                    WaitGame waitGame = new WaitGame(client);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/waitGame.fxml"));

                    loader.setController(waitGame);
                    Pane mainPane = loader.load();

                    Scene scene = new Scene(mainPane, 1000, 720);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.showAndWait();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
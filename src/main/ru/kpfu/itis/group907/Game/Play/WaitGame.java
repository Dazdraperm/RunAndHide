package kpfu.itis.group907.Game.Play;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kpfu.itis.group907.Game.Server.udp.Client.Client;
import kpfu.itis.group907.Game.Server.udp.Server.Server;

import java.io.IOException;

public class WaitGame {
    private Client client;

    public WaitGame(Client client) {
        this.client = client;
    }


    @FXML
    private Button buttonReady;

    @FXML
    private ComboBox<?> boxPersonage;

    @FXML
    private Label countReady;

    @FXML
    private Label labelNick;

    @FXML
    void initialize() {
        labelNick.setText(client.getName());
        client.sendMessage("sosi");
        countReady.setText("1/10");
        buttonReady.setOnAction(event -> {

            buttonReady.getScene().getWindow().hide();
//            if() будет проверка на количество людей, если их 10 то срабатывает нажатие

            try {
                Game game = new Game(client);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/main.fxml"));

                loader.setController(game);
                Pane mainPane = loader.load();

                Scene scene = new Scene(mainPane, 1000, 720);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }


        });
    }

}

package kpfu.itis.group907.Game.Play;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import kpfu.itis.group907.Game.Server.udp.Client;

public class WaitGame {
    private Client client;

    public WaitGame(Client client) {
        this.client = client;
    }

    @FXML
    private Button buttonReady;
    ;

    @FXML
    private ComboBox<?> boxColor;

    @FXML
    private Label countReady;

    @FXML
    private Label labelNick;

    @FXML
    void initialize() {

        labelNick.setText(client.getName());
        countReady.setText("1/10");
    }

}

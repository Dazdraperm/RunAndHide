package kpfu.itis.group907.Game.Play.readyGame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import kpfu.itis.group907.Game.Play.Game;
import kpfu.itis.group907.Game.Server.udp.Client.Client;

import java.io.IOException;


public class WaitGame {

    private boolean isReady = false;
    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    private String countReadyResp = "";


    @FXML
    private Button buttonReady;

    @FXML
    private ComboBox<?> boxPersonage;

    @FXML
    private Label timer;

    @FXML
    private Label countReady;
    private boolean load = false;

    @FXML
    private Label labelNick;
    private boolean checkClient = true;
    private int checkLoad = 0;

    int[] time = {5}; //Чтобы внутри события был доступен, делаем в виде массива.

    private int second = 0;


    @FXML
    void initialize() {

        searchChangeReadyPlayers();

        buttonReady.setOnAction(event -> {
//            countReady.setText(client.getCountReady());
            if (!isReady) {
                buttonReady.setText("You are ready");
                client.sendMessage("ready");

            } else {
                buttonReady.setText("Not ready");
                client.sendMessage("notReady");
            }
            isReady = !isReady;


        });
    }

    private void searchChangeReadyPlayers() {
        Thread thread = new Thread(() -> {
            Runnable updater = () -> {

                if (checkClient) {
                    client.sendMessage("check");
                    checkClient = false;

                }
                countReadyResp = client.getCountReady();

                if (countReadyResp != null) {
                    countReady.setText(countReadyResp);

                }

                if (time[0] == 0) {
                    loadNewScene();

                }

            };

            while (checkLoad <= 0) {
                System.out.println("sda");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // UI update is run on the Application thread
                Platform.runLater(updater);
            }
        });
//        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();
    }

    public void timerDo() {
        Thread thread2 = new Thread(() -> {
            Runnable updater = () -> {
                timer.setVisible(true);
                buttonReady.setVisible(false);
                timer.setText("5 Second to game");
                Timeline timeline = new Timeline(
                        new KeyFrame(
                                Duration.millis(1000), //1000 мс * 60 сек = 1 мин
                                ae -> {
                                    time[0]--;

                                    timer.setText("" + time[0] + " Second to game");

                                }
                        )
                );

                timeline.setCycleCount(5); //Ограничим число повторений
                timeline.play();

            };
            // UI update is run on the Application thread
            Platform.runLater(updater);

        });
        thread2.setDaemon(true);
        thread2.start();


    }


    public void loadNewScene() {
        if (checkLoad <= 0) {
            System.out.println(checkLoad);

            buttonReady.getScene().getWindow().hide();
            checkLoad++;
            try {
                Game game = new Game();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));

                loader.setController(game);
                Pane mainPane = loader.load();

                Scene scene = new Scene(mainPane, 1000, 720);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    public void setLabelNick(String nickname) {
        labelNick.setText(nickname);
    }


}

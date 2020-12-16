package kpfu.itis.group907.Game.Play.readyGame;

import javafx.application.Platform;
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

import java.sql.Time;
import java.util.concurrent.TimeUnit;


public class WaitGame {

    private boolean isReady = false;
    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    private String countReadyResp = "";

    public boolean isReady() {
        return isReady;
    }

    @FXML
    private Button buttonReady;

    @FXML
    private ComboBox<?> boxPersonage;

    @FXML
    private Label timer;

    @FXML
    private Label countReady;

    @FXML
    private Label labelNick;
    private boolean checkClient = true;

    private int second = 0;

    @FXML
    void initialize() {
//        client.sendMessage("sosi");
//        client..sendMessages(message, this);
//        String countReadyLocal = client.getCountReady();


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

            };

            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                // UI update is run on the Application thread
                Platform.runLater(updater);
            }
        });
        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();


        Thread thread2 = new Thread(() -> {
            Runnable updater = () -> {

            };
            // UI update is run on the Application thread
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                Platform.runLater(updater);
            }
        });
        thread2.setDaemon(true);
        thread2.start();
        // don't let thread prevent JVM shutdown


//        Thread thread3 = new Thread(() -> {
//            Runnable updater = () -> {
//
//                if (second != 5) {
//                    if (checkFullReady(countReadyResp)) {
//
//                        double start = getStart();
//                        long now = System.currentTimeMillis();
//
//                        second = (int) ((now - start) / 1000);
//
//
//                        for (; second < 5; ) {
//                            now = System.currentTimeMillis();
//                            second = (int) ((now - start) / 1000);
//                            System.out.println(second);
//                            try {
//                                TimeUnit.SECONDS.sleep(1);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//
//            };
//            // UI update is run on the Application thread
//            while (true) {
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
//                }
//                Platform.runLater(updater);
//            }
//
//        });
//        // don't let thread prevent JVM shutdown
//        thread3.setDaemon(true);
//        thread3.start();


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


//            WaitThread c = new WaitThread(client,countReady);
//            new Thread(c).start();
//            buttonReady.getScene().getWindow().hide();
////            if() будет проверка на количество людей, если их 10 то срабатывает нажатие
//
//            try {
//                Game game = new Game(client);
//
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/main.fxml"));
//
//                loader.setController(game);
//                Pane mainPane = loader.load();
//
//                Scene scene = new Scene(mainPane, 1000, 720);
//                Stage stage = new Stage();
//                stage.setScene(scene);
//                stage.show();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


        });
    }

    public void timerDo() {
        for (int i = 0; i < 5; i++) {
            buttonReady.setVisible(false);
            timer.setVisible(true);
            countReady.setText("full");
            timer.setText(i + "SECOND TO GAME");
            System.out.println(timer.getText());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private double getStart() {
        return System.currentTimeMillis();

    }

    private void startGame(double second) {
        Thread thread = new Thread(() -> {
            Runnable updater = () -> {

            };
            Platform.runLater(updater);
        });

        thread.setDaemon(true);
        thread.start();

    }


    private boolean checkFullReady(String countReadyResp) {
        if (countReadyResp != null && !countReadyResp.equals("")) {

            String[] countReadyArray = countReadyResp.split("/");
            int capacity = Integer.parseInt(countReadyArray[1]);
            int ready = Integer.parseInt(countReadyArray[0]);

            return ready == capacity;
        }
        return false;
    }

    public void setLabelNick(String nickname) {
        labelNick.setText(nickname);
    }

    public void setCountReady(String count) {

    }


}

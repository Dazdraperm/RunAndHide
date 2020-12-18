package kpfu.itis.group907.Game.Play;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import kpfu.itis.group907.Game.Server.udp.Client.Client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Game {
    private Client client;




    private ArrayList<Rectangle> allRectangle = new ArrayList<>();


    private Person person;

    @FXML
    private Label countLives;

    @FXML
    private Rectangle myVisionRectangle;

    @FXML
    private Circle enemyCircle1;

    @FXML
    private Circle myCircle;

    @FXML
    private Circle allyCircle;

    @FXML
    private Circle enemyCircle2;

    @FXML
    private Circle notAllyCircle1;

    @FXML
    private Circle notAllyCircle2;

    @FXML
    private Group map;

    @FXML
    private AnchorPane a;
    ArrayList<String> infoAboutPlayers = new ArrayList<>();

    private HashMap<String, ArrayList<String>> players = new HashMap<>();

    //    Основной метод
    @FXML
    void initialize() {


        countLives.setText(countLives.getText() + " 6");

        setAllRectangle();
//        paintInBlackAllRectangle();
        setSpawnCoordinate();
        person = new Person(myCircle, myVisionRectangle, allRectangle);
        threadSearchChangeOnMap();
        myCircle.sceneProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                person.workWhenMoveCircle();

            }
        }));


    }


    private void threadSearchChangeOnMap() {
        Thread thread1 = new Thread(() -> {
            Runnable updater = () -> {
                infoAboutPlayers.clear();
                infoAboutPlayers.addAll(client.getInfoPlayer());


                if (infoAboutPlayers.size() > 0) {
                    String namePlayer = infoAboutPlayers.get(1);
                    infoAboutPlayers.remove(0);
                    infoAboutPlayers.remove(0);

                    players.put(namePlayer, infoAboutPlayers);
                    System.out.println(players);
                }

            };
            while (true) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(updater);
            }
        });
        thread1.setDaemon(true);
        thread1.start();


    }


    private void paintInBlackAllRectangle() {
        for (Rectangle rectangle : allRectangle) {
            rectangle.setStroke(Color.BLACK);
            rectangle.setFill(Color.BLACK);
        }
    }


    private void setAllRectangle() {
        for (int i = 0; i < map.getChildren().size(); i++) {

            try {

                Rectangle local = (Rectangle) map.getChildren().get(i);

                if (!local.getFill().toString().equals("0x1f93ff00")) {
                    allRectangle.add(local);
                }

            } catch (ClassCastException ignored) {
            }
        }
    }


    private void setSpawnCoordinate() {
        ArrayList<Double> arrayList = spawnHero();
        myCircle.setLayoutX(arrayList.get(0) + myCircle.getRadius());
        myCircle.setLayoutY(arrayList.get(1) + myCircle.getRadius());
    }

    private ArrayList<Double> spawnHero() {
        int randomRectangleIndex = (int) (Math.random() * allRectangle.size());
        Rectangle rectangleSpawn = allRectangle.get(randomRectangleIndex);

        ArrayList<Double> coordinateSpawn = new ArrayList<>();
        coordinateSpawn.add(rectangleSpawn.getLayoutX());
        coordinateSpawn.add(rectangleSpawn.getLayoutY());

        return coordinateSpawn;
    }

    public void setClient(Client client) {
        this.client = client;
        person.setClient(client);
    }
}

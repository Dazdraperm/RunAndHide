package kpfu.itis.group907.Game.Play;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import kpfu.itis.group907.Game.Server.udp.Client.Client;

import java.util.ArrayList;

public class Game {
    private Client client;

    public Game(Client client) {
        this.client = client;

    }


    private ArrayList<Rectangle> allRectangle = new ArrayList<>();


    private Person person;

    @FXML
    private Label countLives;

    @FXML
    private Rectangle myVisionRectangle;

    @FXML
    private Circle enemyCircle;

    @FXML
    private Circle myCircle;

    @FXML
    private Group map;

    @FXML
    private AnchorPane a;

    //    Основной метод
    @FXML
    void initialize() {
        countLives.setText("10");
        setAllRectangle();
//        paintInBlackAllRectangle();
        setSpawnCoordinate();
        person = new Person(myCircle, myVisionRectangle, allRectangle);

        myCircle.sceneProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                person.workWhenMoveCircle();

            }
        }));

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

}

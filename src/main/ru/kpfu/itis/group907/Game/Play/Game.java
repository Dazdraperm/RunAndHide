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
import java.util.Map;

public class Game {
    private Client client;
    private String myName;


    private ArrayList<Rectangle> allRectangle = new ArrayList<>();


    private Person person;

    @FXML
    private Label countLives;

    @FXML
    private Rectangle myVisionRectangle;

//    @FXML
//    private Circle enemyCircle1;

    @FXML
    private Circle myCircle;

    @FXML
    private Circle blueCircle1;

    @FXML
    private Circle blueCircle2;

    @FXML
    private Circle greenCircle1;

    @FXML
    private Circle greenCircle2;

    @FXML
    private Circle redCircle1;

    @FXML
    private Circle redCircle2;

    @FXML
    private Group map;

    @FXML
    private AnchorPane a;
    String infoAboutPlayers = "";

    private HashMap<String, ArrayList<String>> players = new HashMap<>();

    //    Основной метод
    @FXML
    void initialize() {


        countLives.setText(countLives.getText() + " 6");

        setAllRectangle();
//        paintInBlackAllRectangle();
        setSpawnCoordinate();
        person = new Person(myCircle, myVisionRectangle, allRectangle);
        ArrayList<Rectangle> allRectangle = person.getAllRectangle();
        threadSearchChangeOnMap();
        threadSearchChangeOnMapAlways();
        myCircle.sceneProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                person.workWhenMoveCircle();
//                Rectangle rectangle = new Rectangle();
//                allRectangle.forEach(e -> System.out.println(e.intersects(rectangle.getBoundsInParent())));
//                if(rectangle.intersects())
            }
        }));


    }

    private int i = 0;


    //    private
    private void threadSearchChangeOnMap() {
        Thread thread1 = new Thread(() -> {
            Runnable updater = () -> {
//                infoAboutPlayers = client.getInfoPlayer();
//                System.out.println(client.getRedTeam() + " " + client.getBlueTeam() + " " + client.getGreenTeam());
//                if (setPlayers){
//                    System.out.println(client.getBlueTeam() + " " + client.getGreenTeam());
                wasd();

                if (client.getColor().equals("BLUE")) {

//                    myCircle.setFill(Color.BLUE);
//                    blueCircle1 = myCircle;
                } else if (client.getColor().equals("RED")) {
                    myCircle.setFill(Color.RED);
//                    redCircle1 = myCircle;
                } else if (client.getColor().equals("GREEN")) {

                    myCircle.setFill(Color.GREEN);
//                    greenCircle1 = myCircle;
                }

//                    setPlayers = false;
//                }
//                blueCircle1.setLayoutX((double) client.getBlueTeam().entrySet());

            };
            while (i < 5) {
                try {
                    Thread.sleep(25);
                    i++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(updater);

            }
        });
        thread1.setDaemon(true);
        thread1.start();
    }

    private void wasd() {

        if (client.getGreenCircle1().size() > 0 && client.getGreenCircle2().size() > 0) {
            greenCircle1.setLayoutX(Double.parseDouble(client.getGreenCircle1().get(0)));
            greenCircle1.setLayoutY(Double.parseDouble(client.getGreenCircle1().get(1)));
            greenCircle2.setLayoutX(Double.parseDouble(client.getGreenCircle2().get(0)));
            greenCircle2.setLayoutY(Double.parseDouble(client.getGreenCircle2().get(1)));

        }
        if (client.getBlueCircle1().size() > 0 && client.getBlueCircle2().size() > 0) {

            blueCircle1.setLayoutX(Double.parseDouble(client.getBlueCircle1().get(0)));
            blueCircle1.setLayoutY(Double.parseDouble(client.getBlueCircle1().get(1)));
            blueCircle2.setLayoutX(Double.parseDouble(client.getBlueCircle2().get(0)));
            blueCircle2.setLayoutY(Double.parseDouble(client.getBlueCircle2().get(1)));
        }

        if (client.getRedCircle1().size() > 0 && client.getRedCircle2().size() > 0) {
            redCircle1.setLayoutX(Double.parseDouble(client.getRedCircle1().get(0)));
            redCircle1.setLayoutY(Double.parseDouble(client.getRedCircle1().get(1)));
            redCircle2.setLayoutX(Double.parseDouble(client.getRedCircle2().get(0)));
            redCircle2.setLayoutY(Double.parseDouble(client.getRedCircle2().get(1)));

        }
    }

    private void threadSearchChangeOnMapAlways() {
        Thread thread2 = new Thread(() -> {
            Runnable updater = () -> {

//                infoAboutPlayers = client.getInfoPlayer();
//                System.out.println(client.getRedTeam() + " " + client.getBlueTeam() + " " + client.getGreenTeam());
//                if (setPlayers){
//                    System.out.println(client.getBlueTeam() + " " + client.getGreenTeam());
                wasd();

//                    setPlayers = false;
//                }
//                blueCircle1.setLayoutX((double) client.getBlueTeam().entrySet());

            };
            while (true) {
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(updater);

            }
        });
        thread2.setDaemon(true);
        thread2.start();


    }

//    private void setPlayers(){
//        int i = 0;
//        for(Map.Entry<String, ArrayList<String>> item : client.getBlueTeam().entrySet()){
//            ArrayList<String> information = item.getValue();
//            if(i == 0) {
//                blueCircle1.setLayoutX(Double.parseDouble(information.get(2)));
//                blueCircle1.setLayoutY(Double.parseDouble(information.get(3)));
//            }
//            else if(i == 1){
//                blueCircle2.setLayoutX(Double.parseDouble(information.get(2)));
//                blueCircle2.setLayoutY(Double.parseDouble(information.get(3)));
//            }
//            i++;
////            System.out.printf("Key: %s  Value: %s \n", item.getKey(), item.getValue());
//        }
//        int j = 0;
//        for(Map.Entry<String, ArrayList<String>> item : client.getGreenTeam().entrySet()){
//            ArrayList<String> information = item.getValue();
//            if(j == 0) {
//                greenCircle1.setLayoutX(Double.parseDouble(information.get(2)));
//                greenCircle1.setLayoutY(Double.parseDouble(information.get(3)));
//            }
//            else if(j == 1){
//                greenCircle2.setLayoutX(Double.parseDouble(information.get(2)));
//                greenCircle2.setLayoutY(Double.parseDouble(information.get(3)));
//            }
//            j++;
////            System.out.printf("Key: %s  Value: %s \n", item.getKey(), item.getValue());
//        }
//        int k = 0;
//        for(Map.Entry<String, ArrayList<String>> item : client.getRedTeam().entrySet()){
//            ArrayList<String> information = item.getValue();
//            if(k == 0) {
//                redCircle1.setLayoutX(Double.parseDouble(information.get(2)));
//                redCircle1.setLayoutY(Double.parseDouble(information.get(3)));
//            }
//            else if(k == 1){
//                redCircle2.setLayoutX(Double.parseDouble(information.get(2)));
//                redCircle2.setLayoutY(Double.parseDouble(information.get(3)));
//            }
//            k++;
////            System.out.printf("Key: %s  Value: %s \n", item.getKey(), item.getValue());
//        }
//    }

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
        myName = client.getName();
    }
}

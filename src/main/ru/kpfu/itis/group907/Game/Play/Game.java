package kpfu.itis.group907.Game.Play;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import kpfu.itis.group907.Game.Server.udp.Client;

import java.util.ArrayList;

public class Game {
    private Client client;

    public Game(Client client) {
        this.client = client;

    }

    //    Блоки которые должен видеть пользователь
    private ArrayList<Rectangle> playerVisionBlocks = new ArrayList<>();

    //    Блоки которые НЕ видит пользователь
    private ArrayList<Rectangle> playerNotVisionBlocks = new ArrayList<>();

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

        myCircle.sceneProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                moveCircle();
            }
        }));

    }

    //    Передвижение персонажа и фигуры, которая служит для подсветки блоков
    private void moveCircle() {
        myCircle.getScene().setOnKeyPressed(key -> {
            changeColorBlockPlayer();
            changeColorNotBlockPlayer();
            if (key.getCode() == KeyCode.getKeyCode("W")) {

                if (checkCollision("Top")) {
                    myVisionRectangle.setLayoutY(myVisionRectangle.getLayoutY() - 5);

                    checkCollisionVisionBlock();
                    myCircle.setLayoutY(myCircle.getLayoutY() - 5);
                }
            }

            if (key.getCode() == KeyCode.getKeyCode("S")) {

                if (checkCollision("Down")) {
                    myVisionRectangle.setLayoutY(myVisionRectangle.getLayoutY() + 5);

                    checkCollisionVisionBlock();
                    myCircle.setLayoutY(myCircle.getLayoutY() + 5);
                }
            }

            if (key.getCode() == KeyCode.getKeyCode("A")) {

                if (checkCollision("Left")) {
                    myVisionRectangle.setLayoutX(myVisionRectangle.getLayoutX() - 5);

                    checkCollisionVisionBlock();

                    myCircle.setLayoutX(myCircle.getLayoutX() - 5);
                }
            }

            if (key.getCode() == KeyCode.getKeyCode("D")) {
                if (checkCollision("Right")) {
                    myVisionRectangle.setLayoutX(myVisionRectangle.getLayoutX() + 5);

                    checkCollisionVisionBlock();
                    myCircle.setLayoutX(myCircle.getLayoutX() + 5);


                }
            }

        });
    }

    //    Перекрашиваются блоки, которые должен видеть пользователь
    private void changeColorBlockPlayer() {
        if (playerVisionBlocks != null) {
            for (Rectangle playerVisionBlock : playerVisionBlocks) {
                playerVisionBlock.setFill(Color.rgb(41, 40, 56));
            }
        }

    }

    //    Перекрашиваются блоки, которые не должен видеть пользователь
    private void changeColorNotBlockPlayer() {
        if (playerNotVisionBlocks != null) {
            for (Rectangle playerNotVisionBlock : playerNotVisionBlocks) {
                playerNotVisionBlock.setFill(Color.rgb(17, 16, 23));
            }
        }

    }


    private boolean checkCollision(String side) {
        playerVisionBlocks.clear();
        playerNotVisionBlocks.clear();

        for (int i = 0; i < map.getChildren().size(); i++) {
            try {
                Rectangle local = (Rectangle) map.getChildren().get(i);
                if (local.getFill().toString().equals("0x1f93ff00")) continue;
                Bounds back = local.getBoundsInParent();
                switch (side) {
                    case "Top":

                        if (back.intersects(myCircle.getBoundsInParent().getMinX(),
                                myCircle.getBoundsInParent().getMinY() - 2 * myCircle.getRadius(),
                                myCircle.getBoundsInParent().getMinZ(),
                                myCircle.getBoundsInParent().getWidth(),
                                myCircle.getBoundsInParent().getHeight(),
                                myCircle.getBoundsInParent().getDepth())) {

                            return true;
                        }
                        break;
                    case "Down":
                        if (back.intersects(myCircle.getBoundsInParent().getMinX(),
                                myCircle.getBoundsInParent().getMinY() + 2 * myCircle.getRadius(),
                                myCircle.getBoundsInParent().getMinZ(),
                                myCircle.getBoundsInParent().getWidth(),
                                myCircle.getBoundsInParent().getHeight(),
                                myCircle.getBoundsInParent().getDepth())) {
                            return true;
                        }
                        break;
                    case "Left":

                        if (back.intersects(myCircle.getBoundsInParent().getMinX() - 2 * myCircle.getRadius(),
                                myCircle.getBoundsInParent().getMinY(),
                                myCircle.getBoundsInParent().getMinZ(),
                                myCircle.getBoundsInParent().getWidth(),
                                myCircle.getBoundsInParent().getHeight(),
                                myCircle.getBoundsInParent().getDepth())) {
                            return true;
                        }
                        break;
                    case "Right":

                        if (back.intersects(myCircle.getBoundsInParent().getMinX() + 2 * myCircle.getRadius(),
                                myCircle.getBoundsInParent().getMinY(),
                                myCircle.getBoundsInParent().getMinZ(),
                                myCircle.getBoundsInParent().getWidth(),
                                myCircle.getBoundsInParent().getHeight(),
                                myCircle.getBoundsInParent().getDepth())) {
                            return true;
                        }
                        break;

                }


            } catch (ClassCastException ignored) {
            }
        }
        return false;
    }

    //  Проверяются все блоки, которые будет видеть пользователь и записываются в playerVisionBlocks, которые не видить, тоже записываются но playerNotVisionBlocks
    private void checkCollisionVisionBlock() {
        for (int i = 0; i < map.getChildren().size(); i++) {
            try {
                Rectangle local = (Rectangle) map.getChildren().get(i);

//                Вокруг персноажа есть квадрат, который не должен проверятся на коллизию с персонажем
                if (local.getFill().toString().equals("0x1f93ff00")) continue;
                Bounds back = local.getBoundsInParent();

//                Проверка на коллизию
                if (helpCheckCollisionVisionBlock(back)) {
                    playerVisionBlocks.add(local);
                } else playerNotVisionBlocks.add(local);
            } catch (ClassCastException e) {

            }
        }

    }

    private boolean helpCheckCollisionVisionBlock(Bounds back) {
        return back.intersects(myVisionRectangle.getBoundsInParent());
    }
}

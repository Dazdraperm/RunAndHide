package kpfu.itis.group907.Game.Play;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import kpfu.itis.group907.Game.Server.udp.Client;

public class Game {
    private Client client;

    public Game(Client client) {
        this.client = client;

    }


    @FXML
    private Label countLives;

    @FXML
    private Circle enemyCircle;

    @FXML
    private Circle myCircle;

    @FXML
    private Group map;

    @FXML
    private AnchorPane a;

    @FXML
    void initialize() {
        countLives.setText("10");

        myCircle.sceneProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                moveCircle();

            }
        }));

    }

    private void moveCircle() {
        myCircle.getScene().setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.getKeyCode("W")) {
                if (checkCollision("Top")) myCircle.setLayoutY(myCircle.getLayoutY() - 5);


            }

            if (key.getCode() == KeyCode.getKeyCode("S")) {
                if (checkCollision("Down")) myCircle.setLayoutY(myCircle.getLayoutY() + 5);
            }

            if (key.getCode() == KeyCode.getKeyCode("A")) {
                if (checkCollision("Left")) myCircle.setLayoutX(myCircle.getLayoutX() - 5);
            }

            if (key.getCode() == KeyCode.getKeyCode("D")) {
                if (checkCollision("Right")) myCircle.setCenterX(myCircle.getCenterX() + 5);
            }

        });
    }

    private boolean checkCollision(String side) {
        for (int i = 0; i < map.getChildren().size(); i++) {
            try {
                AnchorPane local = (AnchorPane) map.getChildren().get(i);
                Bounds back = local.getBoundsInParent();
                switch (side) {
                    case "Top":
                        if (back.intersects(myCircle.getBoundsInParent().getMinX(),
                                myCircle.getBoundsInParent().getMinY() - 2 * myCircle.getRadius(),
                                myCircle.getBoundsInParent().getMinZ(),
                                myCircle.getBoundsInParent().getWidth(),
                                myCircle.getBoundsInParent().getHeight(),
                                myCircle.getBoundsInParent().getDepth())) {
                            System.out.println(back + "           " + myCircle.getBoundsInParent());
                            return true;
                        }
                        break;
                    case "Down":
                        if (back.intersects(myCircle.getBoundsInParent().getMinX(),
                                myCircle.getBoundsInParent().getMinY() + 2 * myCircle.getRadius(),
                                myCircle.getBoundsInParent().getMinZ(),
                                myCircle.getBoundsInParent().getWidth(),
                                myCircle.getBoundsInParent().getHeight(),
                                myCircle.getBoundsInParent().getDepth())) return true;
                        break;
                    case "Left":

                        if (back.intersects(myCircle.getBoundsInParent().getMinX() - 2 * myCircle.getRadius(),
                                myCircle.getBoundsInParent().getMinY(),
                                myCircle.getBoundsInParent().getMinZ(),
                                myCircle.getBoundsInParent().getWidth(),
                                myCircle.getBoundsInParent().getHeight(),
                                myCircle.getBoundsInParent().getDepth())) return true;
                        break;
                    case "Right":

                        if (back.intersects(myCircle.getBoundsInParent().getMinX() + 2 * myCircle.getRadius(),
                                myCircle.getBoundsInParent().getMinY(),
                                myCircle.getBoundsInParent().getMinZ(),
                                myCircle.getBoundsInParent().getWidth(),
                                myCircle.getBoundsInParent().getHeight(),
                                myCircle.getBoundsInParent().getDepth())) return true;
                        break;

                }


            } catch (ClassCastException ignored) {
            }
        }
        return false;
    }

}

package kpfu.itis.group907.Game.Play;

import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import kpfu.itis.group907.Game.Server.udp.Client.Client;

import java.util.ArrayList;

public class Person {
    private Circle myCircle;
    private Double layoutX;
    private Double layoutY;
    private Bounds bounds;
    private Rectangle myVisionRectangle;
    private Rectangle playerOnThisBlock;

    public void setClient(Client client) {
        this.client = client;
        if (client != null) {
            client.sendMessage("moveCircle " + client.getName() + " " + this.getLayoutX() + " " + this.getLayoutY());
        }
    }

    private ArrayList<Rectangle> allRectangle;
    private Client client;

    //    Блоки которые должен видеть пользователь
    private ArrayList<Rectangle> playerVisionBlocks = new ArrayList<>();

    //    Блоки которые НЕ видит пользователь
    private ArrayList<Rectangle> playerNotVisionBlocks = new ArrayList<>();


    public Person(Circle myCircle, Rectangle myVisionRectangle,
                  ArrayList<Rectangle> allRectangle) {
        this.myCircle = myCircle;
        this.layoutX = myCircle.getLayoutX();
        this.layoutY = myCircle.getLayoutY();
        this.bounds = myCircle.getBoundsInParent();
        this.myVisionRectangle = myVisionRectangle;
        this.allRectangle = allRectangle;

    }

    private boolean checkCollision(String side) {
        playerVisionBlocks.clear();
        playerNotVisionBlocks.clear();

        for (Rectangle rectangle : allRectangle) {
            Bounds back = rectangle.getBoundsInParent();

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
        }
        return false;
    }

    //    Движение круга
    private void moveCircle(KeyEvent key) {
        int speed = 5;
        int halfVision = (int) myVisionRectangle.getHeight() / 2;
        if (key.getCode() == KeyCode.getKeyCode("W")) {

            if (checkCollision("Top")) {
                myVisionRectangle.setLayoutY(myCircle.getLayoutY() - speed);

                myVisionRectangle.setLayoutX(myCircle.getLayoutX() - halfVision);
                myVisionRectangle.setLayoutY(myCircle.getLayoutY() - halfVision);
                checkCollisionVisionBlock();
                myCircle.setLayoutY(myCircle.getLayoutY() - speed);
            }
        }

        if (key.getCode() == KeyCode.getKeyCode("S")) {

            if (checkCollision("Down")) {
                myVisionRectangle.setLayoutY(myCircle.getLayoutY() + speed);


                myVisionRectangle.setLayoutX(myCircle.getLayoutX() - halfVision);
                myVisionRectangle.setLayoutY(myCircle.getLayoutY() - halfVision);
                checkCollisionVisionBlock();
                myCircle.setLayoutY(myCircle.getLayoutY() + speed);
            }
        }

        if (key.getCode() == KeyCode.getKeyCode("A")) {

            if (checkCollision("Left")) {
                myVisionRectangle.setLayoutX(myCircle.getLayoutX() - speed);


                myVisionRectangle.setLayoutX(myCircle.getLayoutX() - halfVision);
                myVisionRectangle.setLayoutY(myCircle.getLayoutY() - halfVision);
                checkCollisionVisionBlock();

                myCircle.setLayoutX(myCircle.getLayoutX() - speed);
            }
        }

        if (key.getCode() == KeyCode.getKeyCode("D")) {
            if (checkCollision("Right")) {
                myVisionRectangle.setLayoutX(myCircle.getLayoutX() + speed);


                myVisionRectangle.setLayoutX(myCircle.getLayoutX() - halfVision);
                myVisionRectangle.setLayoutY(myCircle.getLayoutY() - halfVision);


                checkCollisionVisionBlock();
                myCircle.setLayoutX(myCircle.getLayoutX() + speed);


            }
        }
        this.layoutX = myCircle.getLayoutX();
        this.layoutY = myCircle.getLayoutY();
        if (client != null) {
            client.sendMessage("moveCircle " + client.getName() + " " + this.getLayoutX() + " " + this.getLayoutY());
        }

    }

    //  Проверяются все блоки, которые будет видеть пользователь и записываются в playerVisionBlocks, которые не видить, тоже записываются но playerNotVisionBlocks
    private void checkCollisionVisionBlock() {
        for (Rectangle rectangle : allRectangle) {
            Bounds back = rectangle.getBoundsInParent();

//          Проверка на коллизию
            if (helpCheckCollisionVisionBlock(back)) {
                playerVisionBlocks.add(rectangle);
            } else playerNotVisionBlocks.add(rectangle);

        }


    }

    private boolean helpCheckCollisionVisionBlock(Bounds back) {
        double checkPlayerOnThisBlock = (1.0 * playerOnThisBlock.getWidth()) / (1.0 * playerOnThisBlock.getHeight());
        double checkAboutPlayer = (1.0 * back.getWidth()) / (1.0 * back.getHeight());
        boolean checkCollision = back.intersects(myVisionRectangle.getBoundsInParent());
        boolean checkOnOneLineX = back.getMinX() == playerOnThisBlock.getBoundsInParent().getMinX();
        boolean checkOnOneLineY = back.getMinY() == playerOnThisBlock.getBoundsInParent().getMinY();

        if ((checkPlayerOnThisBlock >= 1.0) && (checkAboutPlayer >= 1.0) && checkCollision && checkOnOneLineY) {
            return true;
        } else if ((checkPlayerOnThisBlock <= 1.0) && (checkAboutPlayer <= 1.0) && checkCollision && checkOnOneLineX) {
            return true;
        } else {
            return false;
        }
    }

    //  Функции выполняющиеся при движение круга
    public void workWhenMoveCircle() {
        myCircle.getScene().setOnKeyPressed(key -> {
            setPlayerOnThisBlock();
            moveCircle(key);
            changeColorBlockPlayer();
            changeColorNotBlockPlayer();
        });
    }


    //    Перекрашиваются блоки, которые должен видеть пользователь
    private void changeColorBlockPlayer() {
        if (playerVisionBlocks != null) {
            for (Rectangle playerVisionBlock : playerVisionBlocks) {
//                playerVisionBlock.setStroke(Color.rgb(41, 40, 56));
                playerVisionBlock.setFill(Color.rgb(41, 40, 56));
            }
        }

    }

    //    Перекрашиваются блоки, которые не должен видеть пользователь
    private void changeColorNotBlockPlayer() {
        if (playerNotVisionBlocks != null) {
            for (Rectangle playerNotVisionBlock : playerNotVisionBlocks) {
                if (playerNotVisionBlock.getFill().toString().equals("0x292838ff")) {
//                    playerNotVisionBlock.setStroke(Color.rgb(17, 16, 23));
                    playerNotVisionBlock.setFill(Color.rgb(17, 16, 23));
                }
            }
        }

    }


    private void setPlayerOnThisBlock() {
        for (Rectangle rectangle : allRectangle) {
            if (rectangle.getBoundsInParent().intersects(myCircle.getBoundsInParent())) {
                playerOnThisBlock = rectangle;
            }
        }
    }

    public void setMyCircle(Circle myCircle) {
        this.myCircle = myCircle;
    }

    public void setLayoutX(Double layoutX) {
        this.layoutX = layoutX;
    }

    public void setLayoutY(Double layoutY) {
        this.layoutY = layoutY;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public Circle getMyCircle() {
        return myCircle;
    }

    public Double getLayoutX() {
        return layoutX;
    }

    public Double getLayoutY() {
        return layoutY;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public void setMyVisionRectangle(Rectangle myVisionRectangle) {
        this.myVisionRectangle = myVisionRectangle;
    }

    public void setAllRectangle(ArrayList<Rectangle> allRectangle) {
        this.allRectangle = allRectangle;
    }

    public void setPlayerVisionBlocks(ArrayList<Rectangle> playerVisionBlocks) {
        this.playerVisionBlocks = playerVisionBlocks;
    }

    public void setPlayerNotVisionBlocks(ArrayList<Rectangle> playerNotVisionBlocks) {
        this.playerNotVisionBlocks = playerNotVisionBlocks;
    }

    public Rectangle getMyVisionRectangle() {
        return myVisionRectangle;
    }

    public ArrayList<Rectangle> getAllRectangle() {
        return allRectangle;
    }

    public ArrayList<Rectangle> getPlayerVisionBlocks() {
        return playerVisionBlocks;
    }

    public ArrayList<Rectangle> getPlayerNotVisionBlocks() {
        return playerNotVisionBlocks;
    }
}

package kpfu.itis.group907.Game.Server.udp.Client;

//import kpfu.itis.group907.Game.Server.udp.Server.ClientThread;

import kpfu.itis.group907.Game.Play.Game;
import kpfu.itis.group907.Game.Play.readyGame.WaitGame;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Client {

    private Socket socket;
    //    private ClientThread clientThread;
    private String name;
    private String countReady;
    ClientThread clientThread;
    private WaitGame waitGame;
    private Game game;

    public ArrayList<String> getInfoPlayer() {
        return infoPlayer;
    }

    public void setInfoPlayer(ArrayList<String> infoPlayer) {
        this.infoPlayer = infoPlayer;
    }

    private ArrayList<String> infoPlayer = new ArrayList<>();


    public Client(String name, WaitGame waitGame) throws IOException {
        this.name = name;
        this.waitGame = waitGame;
    }


    public void start() throws IOException {
        String host = "localhost";
        int port = 5555;

        socket = new Socket(host, port);

        waitGame.setLabelNick(this.name);
        waitGame.setClient(this);

        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));


        clientThread = new ClientThread(input, output, this);

        //Запускается поток, принимающий инофрмацию с сервера
        new Thread(clientThread).start();
    }


    public void sendMessage(String message) {
        try {
            clientThread.getOutput().write(message + "\n");
            clientThread.getOutput().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* SETTER AND GETTER */
    public void setCountReady(String countReady) {
        this.countReady = countReady;
    }

    public void setGame(Game game) {
        this.game = game;
        game.setClient(this);
    }


    public WaitGame getWaitGame() {
        return waitGame;
    }

    public String getCountReady() {
        return countReady;
    }


    public String getName() {
        return name;
    }
}

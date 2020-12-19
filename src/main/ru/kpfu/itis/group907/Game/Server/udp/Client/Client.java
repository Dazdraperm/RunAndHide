package kpfu.itis.group907.Game.Server.udp.Client;

//import kpfu.itis.group907.Game.Server.udp.Server.ClientThread;

import kpfu.itis.group907.Game.Play.Game;
import kpfu.itis.group907.Game.Play.readyGame.WaitGame;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Client {

    private Socket socket;
    //    private ClientThread clientThread;
    private String name;
    private String countReady;
    ClientThread clientThread;
    private WaitGame waitGame;
    private Game game;

    //    myCircle;
    ArrayList<String> blueCircle1 = new ArrayList<>();
    ArrayList<String> blueCircle2 = new ArrayList<>();

    ArrayList<String> greenCircle1 = new ArrayList<>();
    ArrayList<String> greenCircle2 = new ArrayList<>();
    ArrayList<String> redCircle1 = new ArrayList<>();
    ArrayList<String> redCircle2 = new ArrayList<>();
    private String color = "";


    private HashMap<String, ArrayList<String>> blueTeam = new HashMap<>();
    private HashMap<String, ArrayList<String>> greenTeam = new HashMap<>();
    private HashMap<String, ArrayList<String>> redTeam = new HashMap<>();

    public String getColor() {
        return color;
    }

    public void addBlueTeam(String name, ArrayList<String> informationPlayer) {
        blueTeam.put(name, informationPlayer);
        if (this.name.equals(name)) {
            this.color = "BLUE";
        }
    }

    public void addGreenTeam(String name, ArrayList<String> informationPlayer) {
        greenTeam.put(name, informationPlayer);
        if (this.name.equals(name)) {
            this.color = "GREEN";
        }
    }

    public void addRedTeam(String name, ArrayList<String> informationPlayer) {
        redTeam.put(name, informationPlayer);
        if (this.name.equals(name)) {
            this.color = "RED";
        }
    }


    private String infoPlayer = "";


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

    public ArrayList<String> getBlueCircle1() {
        return blueCircle1;
    }

    public ArrayList<String> getBlueCircle2() {
        return blueCircle2;
    }

    public ArrayList<String> getGreenCircle1() {
        return greenCircle1;
    }

    public ArrayList<String> getGreenCircle2() {
        return greenCircle2;
    }

    public ArrayList<String> getRedCircle1() {
        return redCircle1;
    }

    public ArrayList<String> getRedCircle2() {
        return redCircle2;
    }

    public String getInfoPlayer() {
        return infoPlayer;
    }

    public void setInfoPlayer(String infoPlayer) {
        this.infoPlayer = infoPlayer;
    }

    public WaitGame getWaitGame() {
        return waitGame;
    }

    public String getCountReady() {
        return countReady;
    }

    public HashMap<String, ArrayList<String>> getBlueTeam() {
        return blueTeam;
    }

    public void setBlueCircle1(ArrayList<String> blueCircle1) {
        this.blueCircle1 = blueCircle1;
    }

    public void setBlueCircle2(ArrayList<String> blueCircle2) {
        this.blueCircle2 = blueCircle2;
    }

    public void setGreenCircle1(ArrayList<String> greenCircle1) {
        this.greenCircle1 = greenCircle1;
    }

    public void setGreenCircle2(ArrayList<String> greenCircle2) {
        this.greenCircle2 = greenCircle2;
    }

    public void setRedCircle1(ArrayList<String> redCircle1) {
        this.redCircle1 = redCircle1;
    }

    public void setRedCircle2(ArrayList<String> redCircle2) {
        this.redCircle2 = redCircle2;
    }

    public HashMap<String, ArrayList<String>> getGreenTeam() {
        return greenTeam;
    }

    public HashMap<String, ArrayList<String>> getRedTeam() {
        return redTeam;
    }

    public String getName() {
        return name;
    }

}

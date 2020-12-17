package kpfu.itis.group907.Game.Server.udp.Client;

//import kpfu.itis.group907.Game.Server.udp.Server.ClientThread;

import kpfu.itis.group907.Game.Play.readyGame.WaitGame;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {

    private Socket socket;
    //    private ClientThread clientThread;
    private String name;
    private String countReady;
    ClientThread clientThread;
    private WaitGame waitGame;

    public Client(String name, WaitGame waitGame) throws IOException {
        this.name = name;
        this.waitGame = waitGame;
    }

    public String getCountReady() {
        return countReady;
    }

    public void start() throws IOException {
        String host = "localhost";
        int port = 5555;

        socket = new Socket(host, port);

        waitGame.setLabelNick(this.name);
        waitGame.setClient(this);

        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

//        System.out.println(input.readLine());

        clientThread = new ClientThread(input, output, this);
        new Thread(clientThread).start();
    }

    public void setCountReady(String countReady) {
        this.countReady = countReady;
    }

    public WaitGame getWaitGame() {
        return waitGame;
    }

    public void sendMessage(String message) {
        try {
            clientThread.getOutput().write(message + "\n");
            clientThread.getOutput().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getName() {
        return name;
    }
}

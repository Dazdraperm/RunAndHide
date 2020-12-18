package kpfu.itis.group907.Game.Server.udp.Server;

import kpfu.itis.group907.Game.Server.udp.Client.Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientThread implements Runnable {

    private final BufferedReader input;
    private final BufferedWriter output;
    private final Server server;
    private ArrayList<ClientThread> ready;
    boolean checkCountReady = true;


    private String nameTeam = "";

    public ClientThread(BufferedReader input, BufferedWriter output, Server server) {
        this.input = input;
        this.output = output;
        this.server = server;
    }


    @Override
    public void run() {
        try {
            while (true) {
                methodCheckCountReady();
                String[] inputLine = input.readLine().split(" ");
                if (checkCountReady) {
                    addOrDeleteReady(inputLine[0]);
                }
                if (inputLine[0].equals("moveCircle")) {
//                    if(this )
                    server.sendInfoPosition(this, this.nameTeam, inputLine[1], inputLine[2], inputLine[3]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void methodCheckCountReady() {
        if (server.getReadyClients().size() == server.getServerSize()) {
            checkCountReady = false;
        }
    }

    private void addOrDeleteReady(String string) throws IOException {
        if (string.equals("ready")) {
            server.addReadyClients(this);
            server.sendInfoAboutReadyPlayer();

        } else if (string.equals("notReady")) {
            server.removeReadyClients(this);
            server.sendInfoAboutReadyPlayer();
        } else {
            server.sendInfoAboutReadyPlayer();
        }
    }

    public void setNameTeam(String nameTeam) {
        this.nameTeam = nameTeam;
    }

    public String getNameTeam() {
        return nameTeam;
    }

    public BufferedReader getInput() {
        return input;
    }

    public BufferedWriter getOutput() {
        return output;
    }

    public Server getServer() {
        return server;
    }


    public void setClients(ArrayList<ClientThread> ready) {
        this.ready = ready;
    }
}

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
                String inputLine = input.readLine();
                if (checkCountReady) {
                    if (inputLine.equals("ready")) {
                        server.addReadyClients(this);
                        server.sendInfoAboutReadyPlayer();

                    } else if (inputLine.equals("notReady")) {
                        server.removeReadyClients(this);
                        server.sendInfoAboutReadyPlayer();
                    } else {
                        server.sendInfoAboutReadyPlayer();
                    }
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

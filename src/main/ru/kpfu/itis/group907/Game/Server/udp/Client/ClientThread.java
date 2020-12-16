package kpfu.itis.group907.Game.Server.udp.Client;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ClientThread implements Runnable {

    private final BufferedReader input;
    private final BufferedWriter output;
    private final Client client;

    public ClientThread(BufferedReader input, BufferedWriter output, Client client) {
        this.input = input;
        this.output = output;
        this.client = client;
    }

    public BufferedReader getInput() {
        return input;
    }

    public BufferedWriter getOutput() {
        return output;
    }
    @Override
    public void run() {
        try {
            while (true) {
                String respCountReady = input.readLine();
//                client.getWaitGame().setCountReady(respCountReady);
                client.setCountReady(respCountReady);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


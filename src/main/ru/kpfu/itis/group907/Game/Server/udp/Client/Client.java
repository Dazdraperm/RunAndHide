package kpfu.itis.group907.Game.Server.udp.Client;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {

    private Socket socket;
    private ClientThread clientThread;
    private String name;


    public Client(String name) {
        this.name = name;
    }

    public void sendMessage(String message) {
        try {
            clientThread.getOutput().write(message);
            clientThread.getOutput().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        String host = "localhost";
        int port = 5555;

        socket = new Socket(host, port);

        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

        clientThread = new ClientThread(input, output, this);

        new Thread(clientThread).start();
    }

    public String getName() {
        return name;
    }
}

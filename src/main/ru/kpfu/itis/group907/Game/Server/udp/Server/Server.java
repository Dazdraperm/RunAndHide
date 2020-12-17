package kpfu.itis.group907.Game.Server.udp.Server;

//import kpfu.itis.group907.Game.Server.udp.Server.ClientThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import java.util.ArrayList;

public class Server {

    private static final int PORT = 5555;
    private ServerSocket socket;
    private ArrayList<ClientThread> readyClients = new ArrayList<>();
    private final ArrayList<ClientThread> clients = new ArrayList<>();

    public int getServerSize() {
        return serverSize;
    }

    private final int serverSize = 2;
    ClientThread clientThread;

    public void addReadyClients(ClientThread clientThread) {
        this.readyClients.add(clientThread);
    }

    public void removeReadyClients(ClientThread clientThread) {
        this.readyClients.remove(clientThread);
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
    }

    public void start() throws IOException {
        socket = new ServerSocket(PORT);

        while (true) {
            Socket clientSocket = socket.accept();
            try {
                BufferedWriter output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                clientThread = new ClientThread(input, output, this);
                clients.add(clientThread);


                new Thread(clientThread).start();
            } catch (SocketException e) {
                e.printStackTrace();
                removeClient(clientThread);
            }
        }
    }

    public void sendInfoAboutReadyPlayer() throws IOException {
        for (ClientThread client : clients) {
            client.getOutput().write(readyClients.size() + "/" + serverSize + "\n");
            client.getOutput().flush();
        }
    }

    public ArrayList<ClientThread> getReadyClients() {
        return readyClients;
    }

    public void removeClient(ClientThread clientThread) {
        clients.remove(clientThread);
    }
}
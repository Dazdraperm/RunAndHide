package kpfu.itis.group907.Game.Server.udp.Server;

//import kpfu.itis.group907.Game.Server.udp.Server.ClientThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import java.util.ArrayList;
import java.util.HashSet;

public class Server {

    private static final int PORT = 5555;
    private ServerSocket socket;
    private ArrayList<ClientThread> readyClients = new ArrayList<>();
    private final ArrayList<ClientThread> clients = new ArrayList<>();

    private HashSet<ClientThread> blueTeam = new HashSet<>();
    private HashSet<ClientThread> greenTeam = new HashSet<>();


    private HashSet<ClientThread> redTeam = new HashSet<>();
    private final int serverSize = 2;
    ClientThread clientThread;


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

                divisionIntoTeams(clientThread);

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

    public void sendInfoPosition(ClientThread clientThread, String name, String nameTeam, String LayoutX, String LayoutY) throws IOException {
        for (ClientThread client : clients) {
            if (client != clientThread) {
                if (client.getNameTeam().equals(nameTeam)) {

                    client.getOutput().write("position " + nameTeam + " " + name + " ally " + LayoutX + " " + LayoutY + "\n");

                } else if (!client.getNameTeam().equals(nameTeam) && !client.getNameTeam().equals("RED")) {
                    client.getOutput().write("position " + nameTeam + " " + name + " notAlly " + LayoutX + " " + LayoutY + "\n");

                } else {

                    client.getOutput().write("position RED " + name + " enemy " + LayoutX + " " + LayoutY + "\n");

                }

                client.getOutput().flush();
            }
        }
    }


    private void divisionIntoTeams(ClientThread clientThread) {
        if (blueTeam.size() < 2) {
            blueTeam.add(clientThread);
            clientThread.setNameTeam("BLUE");
        } else if (greenTeam.size() < 2) {
            greenTeam.add(clientThread);
            clientThread.setNameTeam("GREEN");

        } else if (redTeam.size() < 2) {
            redTeam.add(clientThread);
            clientThread.setNameTeam("RED");

        }

    }

    public void addReadyClients(ClientThread clientThread) {
        this.readyClients.add(clientThread);
    }

    public void removeReadyClients(ClientThread clientThread) {
        this.readyClients.remove(clientThread);
    }

    public void removeClient(ClientThread clientThread) {
        clients.remove(clientThread);
    }

    public ArrayList<ClientThread> getReadyClients() {
        return readyClients;
    }

    public int getServerSize() {
        return serverSize;
    }

    public HashSet<ClientThread> blueTeam() {
        return blueTeam;
    }

    public HashSet<ClientThread> greenTeam() {
        return greenTeam;
    }

    public HashSet<ClientThread> getRedTeam() {
        return redTeam;
    }
}
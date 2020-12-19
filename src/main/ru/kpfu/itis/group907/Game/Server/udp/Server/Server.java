package kpfu.itis.group907.Game.Server.udp.Server;

//import kpfu.itis.group907.Game.Server.udp.Server.ClientThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Server {

    private static final int PORT = 5555;
    private ServerSocket socket;
    private ArrayList<ClientThread> readyClients = new ArrayList<>();
    private final ArrayList<ClientThread> clients = new ArrayList<>();

    private HashSet<ClientThread> blueTeam = new HashSet<>();
    private HashSet<ClientThread> greenTeam = new HashSet<>();

    private HashMap<String, String> players = new HashMap<>();

    public void addPlayers(String name, String nameTeam,
                           String LayoutX, String LayoutY,
                           String MinX, String MinY,
                           String MinZ, String Width,
                           String Height, String Depth) throws IOException {
        this.players.put(name, " position " + nameTeam + " " + LayoutX + " " + LayoutY + " "
                + MinX + " " + MinY
                + " " + MinZ + " "
                + Width + " " + Height
                + " " + Depth);
        if (this.players.size() == serverSize) {
            sendInfoPosition();
        }
    }

    public void changePlayers(String name, String nameTeam,
                              String LayoutX, String LayoutY,
                              String MinX, String MinY,
                              String MinZ, String Width,
                              String Height, String Depth) throws IOException {
        this.players.put(name, " position " + nameTeam + " " + LayoutX + " " + LayoutY + " "
                + MinX + " " + MinY
                + " " + MinZ + " "
                + Width + " " + Height
                + " " + Depth);
//        if (this.players.size() == serverSize) {
        sendInfoPosition();
//        }
    }

    private HashSet<ClientThread> redTeam = new HashSet<>();
    private final int serverSize = 4;
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

    public void sendInfoPosition() throws IOException {

//        String infoAboutPlayer = "position " + nameTeam + " " + LayoutX + " " + LayoutY + " "
//                + MinX + " " + MinY
//                + " " + MinZ + " "
//                + Width + " " + Height
//                + " " + Depth + "\n";
//
//        players.put(name, infoAboutPlayer);

        for (ClientThread client : clients) {

//            if (client != clientThread) {


//                if (client.getNameTeam().equals(nameTeam)) {
//
//                      infoAboutPlayer += "ally ";
//
//
//                } else if (!client.getNameTeam().equals(nameTeam) && !client.getNameTeam().equals("RED")) {
//            System.out.println(players.toString());
            client.getOutput().write(players.toString() + "\n");
            client.getOutput().flush();
//                } else {
//
//                    client.getOutput().write("position RED " + name + " enemy "
//                            + infoAboutPlayer + "\n");

        }

//            }
//        }
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
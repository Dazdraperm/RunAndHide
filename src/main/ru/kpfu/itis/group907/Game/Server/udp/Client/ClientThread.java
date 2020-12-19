package kpfu.itis.group907.Game.Server.udp.Client;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

    private int iGreen = 0;
    private int iBlue = 0;
    private int iRed = 0;

    public BufferedWriter getOutput() {
        return output;
    }

    @Override
    public void run() {
        try {
            while (true) {

                String response = input.readLine();
                if (response != null) {

                    String[] resp = response.split(" ");

                    String infoAboutPlayers = response;
                    infoAboutPlayers = infoAboutPlayers.replace("{", "");
                    infoAboutPlayers = infoAboutPlayers.replace("}", "");
//                    Collections.addAll(infoAboutPlayer, resp);
//                    System.out.println(infoAboutPlayers);
                    if (infoAboutPlayers.contains("position")) {

                        String[] arrayInfoAboutPlayers = infoAboutPlayers.split(", ");
                        iGreen = 0;
                        iBlue = 0;
                        iRed = 0;
                        for (int i = 0; i < arrayInfoAboutPlayers.length; i++) {
                            String[] localInform = arrayInfoAboutPlayers[i].split(" ");
                            String name = localInform[0].substring(0, localInform[0].length() - 1);

//                            System.out.println(infoAboutPlayers);
                            switch (localInform[2]) {
                                case "GREEN":
                                    ArrayList<String> greenList = concatenate(localInform);
                                    if (iGreen == 0) {
                                        client.setGreenCircle1(greenList);
                                    } else if (iGreen == 1) {
                                        client.setGreenCircle2(greenList);
                                    }
                                    iGreen++;
                                    client.addGreenTeam(name, greenList);
                                    break;
                                case "BLUE":
                                    ArrayList<String> blueList = concatenate(localInform);
                                    if (iBlue == 0) {
                                        client.setBlueCircle1(blueList);
                                    } else if (iBlue == 1) {
                                        client.setBlueCircle2(blueList);
                                    }
                                    iBlue++;
                                    client.addBlueTeam(name, concatenate(localInform));
                                    break;
                                case "RED":
                                    ArrayList<String> redList = concatenate(localInform);

                                    if (iRed == 0) {
                                        client.setRedCircle1(redList);
                                    } else if (iRed == 1) {
                                        client.setRedCircle2(redList);
                                    }
                                    iRed++;
                                    client.addRedTeam(name, concatenate(localInform));
                                    break;
                            }

                        }

                        client.setInfoPlayer(infoAboutPlayers);
                    } else {

                        client.setCountReady(resp[0]);

                        if (!resp[0].equals("")) {

                            String[] countReadyArray = resp[0].split("/");
                            int capacity = Integer.parseInt(countReadyArray[1]);
                            int ready = Integer.parseInt(countReadyArray[0]);

                            if (ready == capacity) client.getWaitGame().timerDo();

                        }
                    }
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ArrayList<String> concatenate(String[] strings) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 3; i < strings.length; i++) {
            arrayList.add(strings[i]);
        }
//        client.set
        return arrayList;
    }
}


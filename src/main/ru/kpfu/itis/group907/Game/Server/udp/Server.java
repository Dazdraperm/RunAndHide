package kpfu.itis.group907.Game.Server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server extends Thread {

    private final DatagramSocket socket;
    private final byte[] buffer = new byte[512];
    private boolean alive;

    public Server() throws SocketException {
        socket = new DatagramSocket(5556);
    }

    public void run() {
        alive = true;
        while (alive) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(),0, packet.getLength());
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                byte [] data  = message.getBytes();
                packet = new DatagramPacket(data, data.length, address, port);
                socket.send(packet);
                if (message.toLowerCase().equals("bye")) {
                    alive = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }
}
package kpfu.itis.group907.Game.Server.udp;

import java.io.IOException;
import java.net.*;

public class Client {
    private final DatagramSocket socket;
    private byte[] buffer;
    private final InetAddress address;
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "address=" + address +
                ", name='" + name + '\'' +
                '}';
    }

    public Client(String name) throws SocketException, UnknownHostException {
        this.name = name;
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }

    public String sendMessage(String message) throws IOException {
        buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 5555);
        socket.send(packet);
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        return new String(packet.getData(), 0, packet.getLength());
    }

    public void stopClient() {
        socket.close();
    }
}

package kpfu.itis.group907.Game.Server.udp;

import java.io.IOException;
import java.util.Scanner;

public class TestClient {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String name = in.next();
        while (name.equals("")) {
            name = in.next();
        }
        Client client = new Client(name);

        System.out.println(client);
        String response = client.sendMessage("hello");
        System.out.println(response);
        while (true) {
            response = client.sendMessage(in.next());
            System.out.println(response);
        }

    }
}

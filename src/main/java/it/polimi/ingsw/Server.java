package it.polimi.ingsw;

import it.polimi.ingsw.network.SocketClient;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PipedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
    public final static int DEFAULT_PORT = 12345;
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        int port = DEFAULT_PORT;

        for (int i = 0; i < args.length; i++)
            if ((args[i].equals("--port") || args[i].equals("-p") ) && args.length > i+1)
                port = Integer.parseInt(args[i+1]);

        ServerSocket serverSocket = null;
        Lobby lobby = new Lobby();

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("error opening socket");
            System.exit(-1);
        }

        System.out.println("--- Server Started ---");

        while (true) {
            try {
                Socket client = serverSocket.accept();

                //new ObjectOutputStream(client.getOutputStream()).writeObject(new it.polimi.ingsw.network.message.Lobby("aa", Arrays.asList("aa", "bb"), 3));;

                System.out.println("new client connected " + client.getInetAddress());

                //new Thread(() -> {lobby.allocateClient(client);});
            } catch (IOException e) {
                System.out.println("connection hangup");
            }
        }
    }
}

package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public final static int DEFAULT_PORT = 12345;
    public final static int SERVER_TIMEOUT = 5000;

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        Map<String, GameController> nickControllerMap = Collections.synchronizedMap(new HashMap<>());


        for (int i = 0; i < args.length; i++)
            if ((args[i].equals("--port") || args[i].equals("-p") ) && args.length > i+1)
                port = Integer.parseInt(args[i+1]);

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("error opening socket");
            System.exit(-1);
        }

        System.out.println("--- Server Started ---");
        System.out.println("Listening on port " + port);

        int clientsCount = 0;
        while (true) {
            try {
                Socket client = serverSocket.accept();
                //serverSocket.setSoTimeout(SERVER_TIMEOUT);

                //new ObjectOutputStream(client.getOutputStream()).writeObject(new Lobby("SERVER", Arrays.asList("aa", "bb"), 3));;

                clientsCount++;
                System.out.println("client " + clientsCount + " connected " + client.getInetAddress());

                new Thread(new ClientHandler(client, nickControllerMap), "clientHandler_" + clientsCount).start();
                //new Thread(() -> {lobby.allocateClient(client);}, "clientHandlerThread_" + clientsCount);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("connection hangup");
            }
        }
    }


}

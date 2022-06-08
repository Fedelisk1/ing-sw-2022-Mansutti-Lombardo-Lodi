package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.view.cli.Cli;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Server's main method.
 */
public class Server {
    public final static int DEFAULT_PORT = 12345;

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

                clientsCount++;
                System.out.println("client " + clientsCount + " connected from " + client.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(client, nickControllerMap);


                // asynchronously handle the newly connected client
                new Thread(clientHandler, "clientHandler_" + clientsCount).start();

                // start server-side heartbeat
                new Thread(new HeartBeatServer(clientHandler)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

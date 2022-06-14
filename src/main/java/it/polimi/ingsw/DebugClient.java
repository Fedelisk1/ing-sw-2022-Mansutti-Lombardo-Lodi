package it.polimi.ingsw;

import it.polimi.ingsw.network.SocketClient;
import it.polimi.ingsw.network.message.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class DebugClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 12345), 10000);
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

        Message login = new LoginRequest("aaa");
        output.writeObject(login);

        while (true) {
            Object read = input.readObject();
            if (read instanceof Message) {
                Message message = (Message) read;
                System.out.println(message.getMessageType());

                if (message.getMessageType() == MessageType.LOGIN_OUTCOME) {
                    LoginOutcome lo = (LoginOutcome) message;

                    System.out.println("login outcome: " + lo.isSuccess());

                    if (lo.getGameId() == -1) {
                        output.writeObject(new NewGameRequest("aaa", 2, true));
                    } else {
                        System.out.println("gameid = " + lo.getGameId());

                    }
                }
            }
        }
    }
}

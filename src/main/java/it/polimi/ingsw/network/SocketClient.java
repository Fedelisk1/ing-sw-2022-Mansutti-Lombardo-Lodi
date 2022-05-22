package it.polimi.ingsw.network;

import it.polimi.ingsw.network.message.ErrorMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Client class to manage messages incoming and outgoing from the client app
 */
public class SocketClient extends Observable {
    private final Socket socket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final ExecutorService readExecutionQueue;
    private static int TIMEOUT = 5000;

    public SocketClient(String serverAddress, int serverPort) throws IOException {
        this.socket = new Socket();
        this.socket.connect(new InetSocketAddress(serverAddress, serverPort), TIMEOUT);
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.readExecutionQueue = Executors.newSingleThreadExecutor();
    }

    public void readMessage() {
        readExecutionQueue.execute(() -> {
            while (!readExecutionQueue.isShutdown()) {
                Message message;
                try {
                    message = (Message) inputStream.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    message = new ErrorMessage(null, "Connection lost with the server.");
                    disconnect();
                    readExecutionQueue.shutdownNow();
                }
                notifyObservers(message);
            }
        });
    }

    public void sendMessage(Message message) {
        try {
            outputStream.writeObject(message);
            outputStream.reset();
            notifyObservers(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void disconnect() {
        try {
            readExecutionQueue.shutdown();
            socket.close();
        } catch (IOException e) {
            // disconnection error
            e.printStackTrace();
        }
    }
}

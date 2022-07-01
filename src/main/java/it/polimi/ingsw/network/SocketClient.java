package it.polimi.ingsw.network;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.Shutdown;
import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Client class to manage messages incoming and outgoing from the client app
 */
public class SocketClient extends Observable {
    private final Socket socket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final static int TIMEOUT = 20000;
    private final AtomicBoolean connected;

    public SocketClient(String serverAddress, int serverPort) throws IOException {
        this.socket = new Socket();
        this.socket.connect(new InetSocketAddress(serverAddress, serverPort), TIMEOUT);
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());

        connected = new AtomicBoolean(true);
    }

    /**
     * reads message about the connection and eventually shutdown client
     */
    public void readMessage() {
        Thread readerThread = new Thread(() -> {
            while (connected.get()) {
                Message message;
                try {
                    message = (Message) inputStream.readObject();

                    // System.out.println("message arrived: " + message.getMessageType());

                    switch (message.getMessageType()) {
                        case PING -> {}
                        case SHUTDOWN_CLIENT -> {
                            connected.set(false);
                            notifyObservers(message);
                        }
                        default -> notifyObservers(message);
                    }
                } catch (SocketTimeoutException timeoutException) {
                    // client-side network disconnection
                    message = new Shutdown("You lost connection with the server.");
                    notifyObservers(message);
                    disconnect();
                } catch (IOException e) {
                    // server has died
                    message = new Message(Message.SERVER_NICKNAME, MessageType.SERVER_UNREACHABLE);
                    notifyObservers(message);
                    disconnect();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        readerThread.start();
    }

    /**
     * send message
     * @param message message
     */
    public void sendMessage(Message message) {
        try {
            outputStream.writeObject(message);
            outputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * closes socket
     */
    public void disconnect() {
        try {
            socket.close();
            connected.set(false);
        } catch (IOException e) {
            // disconnection error
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return connected.get();
    }
}

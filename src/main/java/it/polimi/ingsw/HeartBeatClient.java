package it.polimi.ingsw;

import it.polimi.ingsw.network.SocketClient;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Periodic task that allows the client to send a ping message to the serer in order to notify that he is still connected.
 */
public class HeartBeatClient extends TimerTask implements Runnable {
    private final int PERIOD = 5000;
    private final SocketClient socketClient;
    private final Message message;

    public HeartBeatClient(SocketClient socketClient) {
        this.socketClient = socketClient;
        message = new Message(null, MessageType.PING);
    }

    /**
     * Until the connection is alive, sends a ping message to the server at a fixed period.
     */
    @Override
    public void run() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this::sendPing, 0, PERIOD, TimeUnit.MILLISECONDS);

        boolean connected = true;

        while (connected) {
            if (! socketClient.isConnected()) {
                scheduledExecutorService.shutdownNow();
                connected = false;
            }
        }
    }

    /**
     * Sends a ping message
     */
    private void sendPing() {
        if (socketClient.isConnected())
            socketClient.sendMessage(message);
    }
}

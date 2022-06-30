package it.polimi.ingsw;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HeartBeatServer extends TimerTask implements Runnable {
    private final int PERIOD = 10000;
    private final ClientHandler clientHandler;
    private final Message message;

    public HeartBeatServer(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;

        message = new Message(Message.SERVER_NICKNAME, MessageType.PING);
    }

    @Override
    public void run() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this::sendPing, 0, PERIOD, TimeUnit.MILLISECONDS);

        boolean connected = clientHandler.isConnected();
        while (connected) {
            if (! clientHandler.isConnected()) {
                scheduledExecutorService.shutdownNow();
                connected = false;
            }
        }
    }

    /**
     * Sends a ping message
     */
    private void sendPing() {
        if (clientHandler.isConnected())
            clientHandler.sendMessage(message);
    }
}

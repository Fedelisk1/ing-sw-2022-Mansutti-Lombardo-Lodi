package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exceptions.NoGameAvailableExcpetion;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.TimeoutException;

/**
 * Handles interaction between server and a specific client.
 */
public class ClientHandler implements Runnable {
    private final Map<String, GameController> nickControllerMap;
    private final Socket client;
    private final Object inputLock;
    private final Object outputLock;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean connected;
    private static final int TIMEOUT = 10000;
    private String nickname;

    public ClientHandler(Socket client, Map<String, GameController> nickControllerMap) {
        this.client = client;
        this.nickControllerMap = nickControllerMap;
        this.connected = true;

        inputLock = new Object();
        outputLock = new Object();

        try {
            this.output = new ObjectOutputStream(client.getOutputStream());
            output.flush();
            this.input = new ObjectInputStream(client.getInputStream());
            client.setSoTimeout(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void run() {
        try {
            handleClient();
        } catch (IOException e) {
            connected = false;

            if (nickname == null) {
                System.out.println(threadName() + " client disconnected");
            } else {
                System.out.println(threadName() + " " + nickname + " disconnected");

                GameController controller = nickControllerMap.get(nickname);

                if (controller != null)
                    controller.handleDisconnection(nickname);

                nickControllerMap.remove(nickname);
            }

            disconnect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handleClient() throws IOException, ClassNotFoundException {
        while (connected) {
            synchronized (inputLock) {
                //System.out.println(threadName() + " waiting for message..." );
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Object readObject = input.readObject();

                if (! (readObject instanceof Message msgIn))
                    break;

                if(msgIn.getMessageType() != MessageType.PING)
                    System.out.println(threadName() + " message arrived: " + msgIn.getMessageType());

                switch (msgIn.getMessageType()) {
                    case PING -> {
                        // ping from server arrived
                    }
                    case LOGIN_REQUEST -> {
                        System.out.println("handle login for " + msgIn.getNickname());
                        handleLogin(msgIn.getNickname());
                    }
                    case NEW_GAME_REQUEST -> handleNewGameRequest((NewGameRequest) msgIn);
                    default -> {
                        // forward message to the controller
                        nickControllerMap.get(msgIn.getNickname()).onMessageArrived(msgIn);
                    }
                }
            }
        }
    }

    private void handleLogin(String nickname) {
        System.out.println(threadName() + " validate nickname find " + nickname + " in " + nickControllerMap.keySet() + "");
        if (nickControllerMap.containsKey(nickname)) {
            sendMessage(new LoginOutcome(false, -1)); // gameid should not be relevant
        } else {
            int gameId;
            GameController availableGame = null;
            try {
                availableGame = allocateClient(nickname);
                gameId = availableGame.getId();
            } catch (NoGameAvailableExcpetion e) {
                gameId = -1;
            }

            this.nickname = nickname;

            System.out.println(threadName() + " login ok, gameId = " + gameId);
            sendMessage(new LoginOutcome(true, gameId));
            if (gameId != -1) {
                sendMessage(new Lobby(availableGame.getNicknames(), availableGame.getMaxPlayers()));
                availableGame.startGame();
            }

        }
    }

    private GameController allocateClient(String nickname) throws NoGameAvailableExcpetion {
        boolean availableEmptyGame = false;
        GameController availableGame = null;
        System.out.println(threadName() + " allocating client...");

        for (GameController game : nickControllerMap.values()) {
            //System.out.println("[" + Thread.currentThread().getName() + "] game " + game.getId() + " can be joined: " + game.canBeJoined());
            if (game.canBeJoined()) {
                availableEmptyGame = true;
                availableGame = game;
                nickControllerMap.put(nickname, availableGame);
                break;
            }
        }

        if (availableEmptyGame) {
            System.out.println(threadName() + " added to game " + availableGame.getId());
            VirtualView virtualView = new VirtualView(this);
            availableGame.addPlayer(nickname, virtualView);
            return availableGame;
        } else {
            System.out.println(threadName() + " no joinable game found");
            throw new NoGameAvailableExcpetion();
        }
    }

    private void handleNewGameRequest(NewGameRequest message) {
        int players = message.getPlayers();
        boolean expert = message.isExpertMode();
        VirtualView view = new VirtualView(this);
        int gameId = nickControllerMap.values().size();

        String nickname = message.getNickname();
        GameController newGame = new GameController(players, expert, gameId);

        nickControllerMap.put(nickname, newGame);

        newGame.addPlayer(nickname, view);

        sendMessage(new Lobby(Arrays.asList(nickname), players));
    }

    public void sendMessage(Message message) {
        synchronized (outputLock) {
            try {
                output.writeObject(message);
                output.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        try {
            client.close();

            this.connected = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return connected;
    }

    private String threadName() {
        return "[" + Thread.currentThread().getName() + "]";
    }
}

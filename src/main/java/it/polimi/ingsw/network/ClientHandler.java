package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exceptions.NoGameAvailableExcpetion;
import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

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
            client.setSoTimeout(10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     *
     */
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

                Object readObject = input.readObject();

                if (! (readObject instanceof Message msgIn))
                    break;

                onMessageArrived(msgIn);
            }
        }
    }

    /**
     * dispatch messages received from the server
     * @param message message received
     */
    public void onMessageArrived(Message message) {
        if(message.getMessageType() != MessageType.PING)
            System.out.println(threadName() + " message arrived: " + message.getMessageType());

        switch (message.getMessageType()) {
            case PING -> {
                // ping from server arrived
            }
            case LOGIN_REQUEST -> {
                System.out.println(threadName() + " handle login for " + message.getNickname());
                handleLogin(message.getNickname());
            }
            case NEW_GAME_REQUEST -> handleNewGameRequest((NewGameRequest) message);
            default -> {
                // forward message to the controller
                nickControllerMap.get(message.getNickname()).onMessageArrived(message);
            }
        }
    }

    /**
     * handles login and asserts that nickname is unique
     * @param nickname name to verify
     */
    private void handleLogin(String nickname) {
        System.out.println(threadName() + " validate nickname: find " + nickname + " in " + nickControllerMap.keySet() + "");

        if (nickControllerMap.containsKey(nickname)) {
            // nickname already in use
            sendMessage(new LoginOutcome(false, -1, new ArrayList<>())); // gameid should not be relevant
        } else {
            int gameId;
            List<Wizard> availableWizards;

            GameController availableGame = null;
            try {
                availableGame = allocateClient(nickname);
                gameId = availableGame.getId();
                availableWizards = availableGame.getAvailableWizards();
            } catch (NoGameAvailableExcpetion e) {
                gameId = -1;
                nickControllerMap.put(nickname, null);
                availableWizards = Arrays.stream(Wizard.values()).toList();
            }

            this.nickname = nickname;

            System.out.println(threadName() + " login ok, gameId = " + gameId);
            sendMessage(new LoginOutcome(true, gameId, availableWizards));

        }
    }

    /**
     * allocates client and if there is an available empty game adds the player to it
     * @param nickname name of the player
     * @return
     * @throws NoGameAvailableExcpetion if there isn't any available game
     */
    private GameController allocateClient(String nickname) throws NoGameAvailableExcpetion {
        boolean availableEmptyGame = false;
        GameController availableGame = null;
        System.out.println(threadName() + " allocating client...");

        for (GameController game : nickControllerMap.values()) {
            //System.out.println("[" + Thread.currentThread().getName() + "] game " + game.getId() + " can be joined: " + game.canBeJoined());
            if (game != null && game.canBeJoined()) {
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

    /**
     * handles new game request
     * @param message message NewGameRequest
     */
    private void handleNewGameRequest(NewGameRequest message) {
        int players = message.getPlayers();
        boolean expert = message.isExpertMode();
        VirtualView view = new VirtualView(this);

        synchronized (nickControllerMap) {
            int gameId = nickControllerMap.values().size();

            String nickname = message.getNickname();
            GameController newGame = new GameController(players, expert, gameId);

            nickControllerMap.put(nickname, newGame);

            newGame.addPlayer(nickname, view);
        }
    }

    /**
     * send message
     * @param message message
     */
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

    /**
     * disconnects the client
     */
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

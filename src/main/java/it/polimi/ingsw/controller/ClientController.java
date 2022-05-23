package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.SocketClient;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Handles messages communication with server to achieve a correct game flow client side.
 */
public class ClientController implements ViewObserver, Observer {
    private View view;
    private SocketClient client;
    private ExecutorService taskQueue;
    private String nickname;
    private int gameId;

    public ClientController(View view) {
        this.view = view;
        taskQueue = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onServerInfoInput(Map<String, String> serverInfo) {
        boolean connectionOk = true;
        try {
            client = new SocketClient(serverInfo.get("address"), Integer.parseInt(serverInfo.get("port")));
            client.addObserver(this);
            client.readMessage();
        } catch (IOException | NumberFormatException e) {
            connectionOk = false;
        }

        boolean finalConnectionOk = connectionOk;
        taskQueue.execute(() -> view.showConnectionOutcome(finalConnectionOk));
    }

    @Override
    public void onNicknameInput(String nickname) {
        this.nickname = nickname;
        client.sendMessage(new LoginRequest(nickname));
    }

    @Override
    public void onNewGameParametersInput(int playersNumber, boolean expertMode) {
        client.sendMessage(new NewGameRequest(this.nickname, playersNumber, expertMode));
    }

    /**
     * Dispatch messages received from the server.
     *
     * @param message message received.
     */
    @Override
    public void update(Message message) {
        switch (message.getMessageType()) {
            case LOGIN_OUTCOME:
                LoginOutcome loginOutcome = (LoginOutcome) message;

                if (loginOutcome.isNickAvailable())
                    this.gameId = loginOutcome.getGameId();

                taskQueue.execute(() -> view.showLoginOutcome(
                        loginOutcome.isNickAvailable(),
                        loginOutcome.isNewGame(),
                        nickname
                ));
                break;
            case LOBBY:
                Lobby lobbyMessage = (Lobby) message;
                taskQueue.execute(() -> view.showLobby(lobbyMessage.getNicknames(), lobbyMessage.getPlayers()));
                break;


            case ERROR:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + message.getMessageType());
        }
    }

}
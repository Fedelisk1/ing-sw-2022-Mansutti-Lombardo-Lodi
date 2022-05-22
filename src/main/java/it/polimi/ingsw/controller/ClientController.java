package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.SocketClient;
import it.polimi.ingsw.network.message.LoginOutcome;
import it.polimi.ingsw.network.message.LoginRequest;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Handles messages communication to achieve a correct game flow client side.
 */
public class ClientController implements ViewObserver, Observer {
    private View view;
    private SocketClient client;
    private ExecutorService taskQueue;
    private String nickname;

    public ClientController(View view) {
        this.view = view;
        taskQueue = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onServerInfoInput(Map<String, String> serverInfo) {
        try {
            client = new SocketClient(serverInfo.get("address"), Integer.parseInt(serverInfo.get("port")));
            client.addObserver(this);
            client.readMessage();

            taskQueue.execute(view::nicknameInput);
        } catch (IOException e) {
            taskQueue.execute(() -> view.showLoginOutcome(false, this.nickname));
        }
    }

    @Override
    public void onNicknameInput(String nickname) {
        this.nickname = nickname;
        client.sendMessage(new LoginRequest(nickname));
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
                taskQueue.execute(() -> view.showLoginOutcome(loginOutcome.isLoginSuccess(), nickname));
                break;

            case PLAYERNUMBER_REQUEST:
                taskQueue.execute(() -> {/* view ask playernumber*/});
                break;
            case ERROR:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + message.getMessageType());
        }
    }

}
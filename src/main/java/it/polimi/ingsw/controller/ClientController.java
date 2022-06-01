package it.polimi.ingsw.controller;

import it.polimi.ingsw.HeartBeatClient;
import it.polimi.ingsw.model.Color;
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
    private final View view;
    private SocketClient client;
    private final ExecutorService taskQueue;
    private String nickname;

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

            // start Heartbeat task
            new Thread(new HeartBeatClient(client)).start();
        } catch (IOException | NumberFormatException e) {
            connectionOk = false;
        }

        boolean finalConnectionOk = connectionOk;
        //view.showConnectionOutcome(finalConnectionOk);
        taskQueue.submit(() -> view.showConnectionOutcome(finalConnectionOk));
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

    @Override
    public void onAssistantCardChosen(int chosenCard) {
        client.sendMessage(new PlayAssistantCard(nickname, chosenCard));
    }

    @Override
    public void onStudentMovedToIsland(int island, Color color) {
        client.sendMessage(new MoveStudentToIsland(nickname, island, color));
    }

    @Override
    public void onStudentMovedToDiningRoom(Color color) {
        client.sendMessage(new MoveStudentToDiningRoom(nickname, color));
    }

    @Override
    public void onMotherNatureMoved(int steps) {
        client.sendMessage(new MoveMotherNature(nickname, steps));
    }

    @Override
    public void onCloudCardChosen(int card) {
        client.sendMessage(new ChooseCloudCard(nickname, card));
    }

    @Override
    public void onCCChosen(int card) {
        client.sendMessage(new PlayCharacterCard(nickname, card));
    }

    @Override
    public void onCCAllRemoveColorInput(Color color) {
        client.sendMessage(new CCAllRemoveColorReply(nickname, color));
    }


    /**
     * Dispatch messages received from the server.
     *
     * @param message message received.
     */
    @Override
    public void onMessageArrived(Message message) {
        switch (message.getMessageType()) {
            case PING -> System.out.println("ping from server");
            case LOGIN_OUTCOME -> {
                LoginOutcome loginOutcome = (LoginOutcome) message;

                taskQueue.submit(() -> view.showLoginOutcome(
                        loginOutcome.isSuccess(),
                        loginOutcome.getGameId() == -1,
                        nickname
                ));
            }
            case LOBBY -> {
                Lobby lobbyMessage = (Lobby) message;
                //view.showLobby(lobbyMessage.getNicknames(), lobbyMessage.getPlayers());
                taskQueue.submit(() -> view.showLobby(lobbyMessage.getNicknames(), lobbyMessage.getPlayers()));
            }
            case GAME_START -> {
                GameStart gameStart = (GameStart) message;

            }
            case STRING_MESSAGE -> {
                StringMessage stringMessage = (StringMessage) message;
                //view.showStringMessage(stringMessage.getContent());
                taskQueue.submit(() -> view.showStringMessage(stringMessage.getContent()));
            }
            case ASK_ASSISTANT_CARD -> {
                AskAssistantCard askAssistantCard = (AskAssistantCard) message;
                //view.askAssistantCard(askAssistantCard.getHand(), askAssistantCard.getNotPlayable());
                taskQueue.submit(() -> view.askAssistantCard(askAssistantCard.getHand(), askAssistantCard.getNotPlayable()));
            }
            case ASSISTANT_CARD_PLAYED -> {
                AssistantCardPlayed assistantCardPlayed = (AssistantCardPlayed) message;
                taskQueue.submit(() -> view.showPlayedAssistantCard(assistantCardPlayed.getWhoPlayed(), assistantCardPlayed.getPlayedCard()));
            }
            case ASK_ACTION_PHASE_1 -> {
                AskActionPhase1 askActionPhase1 = (AskActionPhase1) message;
                taskQueue.submit(() -> view.askActionPhase1(askActionPhase1.getCount(), askActionPhase1.getMaxIsland()));
            }
            case ASK_ACTION_PHASE_2 -> {
                AskActionPhase2 askActionPhase2 = (AskActionPhase2) message;
                taskQueue.submit(() -> view.askActionPhase2(askActionPhase2.getMaxMNSteps()));
            }
            case ASK_ACTION_PHASE_3 -> {
                AskActionPhase3 askActionPhase3 = (AskActionPhase3) message;
                taskQueue.submit(() -> view.askActionPhase3(askActionPhase3.getAlloweValues()));
            }
            case ASK_CC_ALL_REMOVE_COLOR_INPUT-> {
                taskQueue.submit(view::askCCAllRemoveColorInput);
            }
            case UPDATE -> {
                Update update = (Update) message;
                taskQueue.submit(() -> view.update(update.getReducedGame()));
            }
            case SHUTDOWN_CLIENT -> {
                Shutdown shutdown = (Shutdown) message;
                taskQueue.submit(() -> view.shutdown(shutdown.getContent()));
            }
            case SERVER_UNREACHABLE -> view.showServerUnreachable();
            case ERROR -> {}
            default -> throw new IllegalStateException("Unexpected value: " + message.getMessageType());

        }
    }
}
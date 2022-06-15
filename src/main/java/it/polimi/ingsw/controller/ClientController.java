package it.polimi.ingsw.controller;

import it.polimi.ingsw.HeartBeatClient;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.SocketClient;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.util.EnumMap;
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
    public void onWizardChosen(Wizard wizard) {
        client.sendMessage(new ChooseWizard(nickname, wizard));
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

    @Override
    public void onCCBlockColorOnceInput(Color color) {
        client.sendMessage(new CCBlockColorOnceReply(nickname, color));
    }

    @Override
    public void onCCChoose1DiningRoomInput(Color color) {
        client.sendMessage(new CCChoose1DiningRoomReply(nickname, color));
    }

    @Override
    public void onCCChose1ToIslandInput(Color color, int island) {
        client.sendMessage(new CCChoose1ToIslandReply(nickname, color, island));
    }

    @Override
    public void onCCChoose3ToEntranceInput(EnumMap<Color, Integer> chosenFromCard, EnumMap<Color, Integer> chosenFromEntrance) {
        client.sendMessage(new CCChoose3ToEntranceReply(nickname, chosenFromCard, chosenFromEntrance));
    }

    @Override
    public void onCCChooseIslandInput(int chosenIsland) {
        client.sendMessage(new CCChooseIslandReply(nickname, chosenIsland));
    }

    @Override
    public void onCCExchange2StudentsInput(EnumMap<Color, Integer> chosenFromEntrance, EnumMap<Color, Integer> chosenFromDiningRoom) {
        client.sendMessage(new CCExchange2StudentsReply(nickname, chosenFromEntrance, chosenFromDiningRoom));
    }

    @Override
    public void onCCNoEntryIslandInput(int chosenIsland) {
        client.sendMessage(new CCNoEntryIslandReply(nickname, chosenIsland));
    }


    /**
     * Dispatch messages received from the server.
     *
     * @param message message received.
     */
    @Override
    public void onMessageArrived(Message message) {
        switch (message.getMessageType()) {
            case PING -> {}
            case LOGIN_OUTCOME -> {
                LoginOutcome loginOutcome = (LoginOutcome) message;

                taskQueue.submit(() -> view.showLoginOutcome(
                        loginOutcome.isSuccess(),
                        loginOutcome.getGameId() == -1,
                        nickname,
                        loginOutcome.getAvailableWizards()
                ));
            }
            case WIZARDS_UPDATE -> {
                WizardsUpdate wizardsUpdate = (WizardsUpdate) message;
                taskQueue.submit(() -> view.updateAvailableWizards(wizardsUpdate.getAvailableWizards()));
            }
            case WIZARD_ERROR -> {
                WizardError wizardErrorMsg = (WizardError) message;
                taskQueue.submit(() -> view.showWizardError(wizardErrorMsg.getAvailableWizards()));
            }
            case LOBBY -> {
                Lobby lobbyMessage = (Lobby) message;
                taskQueue.submit(() -> view.showLobby(lobbyMessage.getPlayers(), lobbyMessage.getPlayersNumber()));
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
                taskQueue.submit(() -> view.askActionPhase1(askActionPhase1.getCount(), askActionPhase1.getMaxIsland(), askActionPhase1.isExpert()));
            }
            case ASK_ACTION_PHASE_2 -> {
                AskActionPhase2 askActionPhase2 = (AskActionPhase2) message;
                taskQueue.submit(() -> view.askActionPhase2(askActionPhase2.getMaxMNSteps(), askActionPhase2.isExpert()));
            }
            case ASK_ACTION_PHASE_3 -> {
                AskActionPhase3 askActionPhase3 = (AskActionPhase3) message;
                taskQueue.submit(() -> view.askActionPhase3(askActionPhase3.getAlloweValues(), askActionPhase3.isExpert()));
            }
            case ASK_CC_ALL_REMOVE_COLOR_INPUT-> {
                taskQueue.submit(view::askCCAllRemoveColorInput);
            }
            case ASK_CC_BLOCK_COLOR_ONCE_INPUT -> {
                taskQueue.submit(view::askCCBlockColorOnceInput);
            }
            case ASK_CC_CHOOSE_1_DINING_ROOM_INPUT -> {
                AskCCChoose1DiningRoomInput askCCChoose1DiningRoomInput = (AskCCChoose1DiningRoomInput) message;
                taskQueue.submit(() -> view.askCCChoose1DiningRoomInput(askCCChoose1DiningRoomInput.getAllowedValues()));
            }
            case ASK_CC_CHOOSE_1_TO_ISLAND_INPUT -> {
                AskCCChoose1ToIslandInput choose1ToIslandInput = (AskCCChoose1ToIslandInput) message;
                taskQueue.submit(() -> view.askCCChoose1ToIslandInput(choose1ToIslandInput.getAllowedColors(), choose1ToIslandInput.getMaxIsland()));
            }
            case ASK_CC_CHOOSE_3_TO_ENTRANCE_INPUT -> {
                AskCCChoose3ToEntranceInput ccChoose3ToEntranceInput = (AskCCChoose3ToEntranceInput) message;
                taskQueue.submit(() -> view.askCCChoose3ToEntranceInput(ccChoose3ToEntranceInput.getAllowedFromCard(), ccChoose3ToEntranceInput.getAllowedFromEntrance()));
            }
            case ASK_CC_CHOOSE_ISLAND_INPUT -> {
                AskCCChooseIslandInput askCCChooseIslandInput = (AskCCChooseIslandInput) message;
                taskQueue.submit(() -> view.askCCChooseIslandInput(askCCChooseIslandInput.getMaxIsland()));
            }
            case ASK_CC_EXCHANGE_2_STUDENTS_INPUT -> {
                AskCCExchange2StudentsInput askCCExchange2StudentsInput = (AskCCExchange2StudentsInput) message;
                taskQueue.submit(() -> view.askCCExchange2StudentsInput(askCCExchange2StudentsInput.getEntrance(), askCCExchange2StudentsInput.getDiningRoom()));
            }
            case ASK_CC_NO_ENTRY_ISLAND_INPUT -> {
                AskCCNoEntryIslandInput askCCNoEntryIslandInput = (AskCCNoEntryIslandInput) message;
                taskQueue.submit(() -> view.askCCNoEntryIslandInput(askCCNoEntryIslandInput.getMaxIsland()));
            }
            case UPDATE -> {
                Update update = (Update) message;
                taskQueue.submit(() -> view.update(update.getReducedGame()));
            }
            case SHUTDOWN_CLIENT -> {
                Shutdown shutdown = (Shutdown) message;
                view.shutdown(shutdown.getContent());
            }
            case WINNER_TO_OTHERS -> {
                WinnerToOthers winnerToOthers = (WinnerToOthers) message;
                taskQueue.submit(() -> view.showWinnerToOthers(winnerToOthers.getNickname()));
            }
            case WINNER -> {
                taskQueue.submit(view::notifyWinner);
            }
            case SERVER_UNREACHABLE -> view.showServerUnreachable();
            case ERROR -> {}
            default -> throw new IllegalStateException("Unexpected value: " + message.getMessageType());
        }
    }
}
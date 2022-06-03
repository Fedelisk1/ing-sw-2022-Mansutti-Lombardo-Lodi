package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.reduced.ReducedGame;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.observer.Observer;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Handles the communication messages between the server and the client server side, making its methods' invocation
 * completely network-transparent for the caller.
 */
public class VirtualView implements View, Observer {
    private final ClientHandler clientHandler;

    public VirtualView(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    /**
     * Receives update message from the model and forwards it to the server over the network.
     * @param message update message
     */
    @Override
    public void onMessageArrived(Message message) {
        clientHandler.sendMessage(message);
    }

    @Override
    public void showConnectionOutcome(boolean connectionOk) {

    }

    @Override
    public void showLoginOutcome(boolean userNameAvailable, boolean newGame, String nickname) {

    }

    @Override
    public void nicknameInput() {

    }

    @Override
    public void showLobby(List<String> nicknames, int players) {

    }

    @Override
    public void askAssistantCard(Map<Integer, Integer> hand, List<Integer> notPlayable) {
        clientHandler.sendMessage(new AskAssistantCard(hand, notPlayable));
    }

    @Override
    public void showStringMessage(String content) {
        clientHandler.sendMessage(new StringMessage(content));
    }

    @Override
    public void shutdown(String message) {
        clientHandler.sendMessage(new Shutdown(message));
    }

    @Override
    public void showServerUnreachable() {

    }

    @Override
    public void showPlayedAssistantCard(String player, int card) {
        clientHandler.sendMessage(new AssistantCardPlayed(player, card));
    }

    @Override
    public void update(ReducedGame game) {
        clientHandler.sendMessage(new Update(game));
    }

    @Override
    public void askActionPhase1(int count, int maxIsland) {
        clientHandler.sendMessage(new AskActionPhase1(count, maxIsland));
    }

    @Override
    public void askActionPhase2(int maxSteps) {
        clientHandler.sendMessage(new AskActionPhase2(maxSteps));
    }

    @Override
    public void askActionPhase3(List<Integer> alloweValues) {
        clientHandler.sendMessage(new AskActionPhase3(alloweValues));
    }

    @Override
    public void askCCAllRemoveColorInput() {
        clientHandler.sendMessage(new AskCCAllRemoveColorInput());
    }

    @Override
    public void askCCBlockColorOnceInput() {
        clientHandler.sendMessage(new AskCCBlockColorOnceInput());
    }

    @Override
    public void askCCChoose1DiningRoomInput(List<Color> allowedValues) {
        clientHandler.sendMessage(new AskCCChoose1DiningRoomInput(allowedValues));
    }
}

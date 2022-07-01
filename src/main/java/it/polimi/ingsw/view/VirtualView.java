package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.model.reduced.ReducedGame;
import it.polimi.ingsw.model.reduced.ReducedPlayer;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.ClientHandler;

import java.net.PortUnreachableException;
import java.util.List;
import java.util.Map;

/**
 * Handles the communication messages between the server and the client server side, making its methods' invocation
 * completely network-transparent for the caller.
 */
public class VirtualView implements View {
    private final ClientHandler clientHandler;

    public VirtualView(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public void showConnectionOutcome(boolean connectionOk) {

    }

    @Override
    public void showLoginOutcome(boolean userNameAvailable, boolean newGame, String nickname, List<Wizard> availableWizards) {

    }
    /**
     * When a player chooses a wizard not present in the list of available
     * @param availableWizards list of available wizard
     */
    @Override
    public void showWizardError(List<Wizard> availableWizards) {
        clientHandler.sendMessage(new WizardError(availableWizards));
    }
    /**
     * Displays the lobby status to the user.
     *
     * @param players players already in the lobby.
     * @param playersNumber maximum number of players of the lobby.
     */
    @Override
    public void showLobby(List<ReducedPlayer> players, int playersNumber) {
        clientHandler.sendMessage(new Lobby(players, playersNumber));
    }
    /**
     * Displays the playable assistant card and asks to choose one
     * @param hand all assistant card in the player's hand
     * @param notPlayable list of not playable cards
     */
    @Override
    public void askAssistantCard(Map<Integer, Integer> hand, List<Integer> notPlayable) {
        clientHandler.sendMessage(new AskAssistantCard(hand, notPlayable));
    }

    @Override
    public void showStringMessage(String content) {
        clientHandler.sendMessage(new StringMessage(content));
    }
    /**
     * Closes the game window when there is a connection error
     * @param message this is a string
     */
    @Override
    public void shutdown(String message) {
        clientHandler.sendMessage(new Shutdown(message));
    }

    @Override
    public void showServerUnreachable() {

    }
    /**
     * When one player wins the game he shows to others the winner's name
     * @param winnerNick name of the winner
     */
    @Override
    public void showWinnerToOthers(String winnerNick) {
        clientHandler.sendMessage(new WinnerToOthers(winnerNick));
    }
    /**
     * Notify the winner
     */
    @Override
    public void notifyWinner() {
        clientHandler.sendMessage(new Winner());
    }

    @Override
    public void showPlayedAssistantCard(String player, int card) {
        clientHandler.sendMessage(new AssistantCardPlayed(player, card));
    }

    @Override
    public void update(ReducedGame game) {
        clientHandler.sendMessage(new Update(game));
    }
    /**
     * Displays the request of action phase 1 and asks where do you want to move the student. It is called a number of times equals to number of players plus one.
     * If the expert mode is active accept the request of playing a character card
     * @param count number of iteration
     * @param maxIsland number of island
     * @param expert true if the expert mode is active
     */
    @Override
    public void askActionPhase1(int count, int maxIsland, boolean expert) {
        clientHandler.sendMessage(new AskActionPhase1(count, maxIsland, expert));
    }
    /**
     * Displays the request of action phase 2 and asks how many steps you want to move mother nature.
     * If the expert mode is active accept the request of playing a character card
     * @param maxSteps number of steps granted by the assistant card played
     * @param expert if the expert mode is active
     */
    @Override
    public void askActionPhase2(int maxSteps, boolean expert) {
        clientHandler.sendMessage(new AskActionPhase2(maxSteps, expert));
    }
    /**
     * Displays the request of action phase 3 and asks a cloud's number to refill the entrance.
     * If the expert mode is active accept the request of playing a character card
     * @param alloweValues list of cloud's number fill of students
     * @param expert if the expert mode is active
     */
    @Override
    public void askActionPhase3(List<Integer> alloweValues, boolean expert) {
        clientHandler.sendMessage(new AskActionPhase3(alloweValues, expert));
    }
    /**
     * Asks color for activation of CC
     */
    @Override
    public void askCCAllRemoveColorInput() {
        clientHandler.sendMessage(new AskCCAllRemoveColorInput());
    }
    /**
     * Asks color for activation of CC
     */
    @Override
    public void askCCBlockColorOnceInput() {
        clientHandler.sendMessage(new AskCCBlockColorOnceInput());
    }
    /**
     * Asks color of student for activation of CC
     * @param allowedValues list of allowed color
     */
    @Override
    public void askCCChoose1DiningRoomInput(List<Color> allowedValues) {
        clientHandler.sendMessage(new AskCCChoose1DiningRoomInput(allowedValues));
    }
    /**
     * Asks color of student for activation of CC
     * @param allowedColors list of allowed color
     * @param maxIsland number of island
     */
    @Override
    public void askCCChoose1ToIslandInput(List<Color> allowedColors, int maxIsland) {
        clientHandler.sendMessage(new AskCCChoose1ToIslandInput(allowedColors, maxIsland));
    }
    /**
     * Asks color for activation of CC showing of allowed color
     * @param allowedFromCC list of allowed color from CC
     * @param allowedFromEntrance list of allowed color from entrance
     * @param inputCount number of iteration
     */
    @Override
    public void askCCChoose3ToEntranceInput(List<Color> allowedFromCC, List<Color> allowedFromEntrance, int inputCount) {
        clientHandler.sendMessage(new AskCCChoose3ToEntranceInput(allowedFromCC, allowedFromEntrance, inputCount));
    }
    /**
     * Asks number of island on which apply the effect of CC
     * @param maxIsland number of island
     */
    @Override
    public void askCCChooseIslandInput(int maxIsland) {
        clientHandler.sendMessage(new AskCCChooseIslandInput(maxIsland));
    }
    /**
     * Asks color for activation of CC showing of allowed color
     * @param entrance list of allowed entrance's color
     * @param diningRoom list of allowed dining room's color
     * @param inputCount number of iteration
     */
    @Override
    public void askCCExchange2StudentsInput(List<Color> entrance, List<Color> diningRoom, int inputCount) {
        clientHandler.sendMessage(new AskCCExchange2StudentsInput(entrance, diningRoom, inputCount));
    }
    /**
     * Asks number of island on which apply the effect of CC
     * @param maxIsland number of island
     */
    @Override
    public void askCCNoEntryIslandInput(int maxIsland) {
        clientHandler.sendMessage(new AskCCNoEntryIslandInput(maxIsland));
    }
    /**
     * After that one player has chosen the wizard, he updates the list of those available
     * @param availableWizards list of available wizards
     */
    public void updateAvailableWizards(List<Wizard> availableWizards) {
        clientHandler.sendMessage(new WizardsUpdate(availableWizards));
    }
}

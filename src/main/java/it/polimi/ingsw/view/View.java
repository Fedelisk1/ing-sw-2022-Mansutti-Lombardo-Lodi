package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.model.reduced.ReducedGame;
import it.polimi.ingsw.model.reduced.ReducedPlayer;

import java.util.List;
import java.util.Map;

/**
 * View interface implemented by the views (cli and gui)
 */
public interface View {
    void showConnectionOutcome(boolean connectionOk);
    void showLoginOutcome(boolean userNameAvailable, boolean newGame, String nickname, List<Wizard> availableWizards);
    void updateAvailableWizards(List<Wizard> availableWizards);
    void showWizardError(List<Wizard> availableWizards);
    void showLobby(List<ReducedPlayer> players, int playersNumber);
    void askAssistantCard(Map<Integer, Integer> hand, List<Integer> notPlayable);
    void showPlayedAssistantCard(String player, int card);
    void update(ReducedGame game);
    void askActionPhase1(int count, int maxIsland, boolean expert);
    void askActionPhase2(int maxMNStpes, boolean expert);
    void askActionPhase3(List<Integer> alloweValues, boolean expert);

    // character
    void askCCAllRemoveColorInput();
    void askCCBlockColorOnceInput();
    void askCCChoose1DiningRoomInput(List<Color> allowedValues);
    void askCCChoose1ToIslandInput(List<Color> allowedColors, int maxIsland);
    void askCCChoose3ToEntranceInput(List<Color> allowedFromCC, List<Color> allowedFromEntrance);
    void askCCChooseIslandInput(int maxIsland);
    void askCCExchange2StudentsInput(List<Color> entrance, List<Color> diningRoom);
    void askCCNoEntryIslandInput(int maxIsland);

    void showStringMessage(String content);
    void shutdown(String message);
    void showServerUnreachable();

    void showWinnerToOthers(String winnerNick);
    void notifyWinner();
}

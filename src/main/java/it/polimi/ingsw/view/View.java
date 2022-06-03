package it.polimi.ingsw.view;

import it.polimi.ingsw.model.reduced.ReducedGame;

import java.util.List;
import java.util.Map;

/**
 * View interface implemented by the views (cli and gui)
 */
public interface View {
    void showConnectionOutcome(boolean connectionOk);
    void showLoginOutcome(boolean userNameAvailable, boolean newGame, String nickname);
    void nicknameInput();
    void showLobby(List<String> nicknames, int players);
    void askAssistantCard(Map<Integer, Integer> hand, List<Integer> notPlayable);
    void showPlayedAssistantCard(String player, int card);
    void update(ReducedGame game);
    void askActionPhase1(int count, int maxIsland);
    void askActionPhase2(int maxMNStpes);
    void askActionPhase3(List<Integer> alloweValues);

    // character
    void askCCAllRemoveColorInput();
    void askCCBlockColorOnceInput();

    void showStringMessage(String content);
    void shutdown(String message);
    void showServerUnreachable();
}

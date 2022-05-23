package it.polimi.ingsw.view;

import java.util.List;

/**
 * View interface implemented by the views (cli and gui)
 */
public interface View {
    void showConnectionOutcome(boolean connectionOk);
    void showLoginOutcome(boolean userNameAvailable, boolean newGame, String nickname);
    void nicknameInput();
    void showLobby(List<String> nicknames, int players);

}

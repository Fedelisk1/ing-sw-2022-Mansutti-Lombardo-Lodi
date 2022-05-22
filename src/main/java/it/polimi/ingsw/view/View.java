package it.polimi.ingsw.view;

/**
 * View interface implemented by the views (cli and gui)
 */
public interface View {
    void nicknameInput();

    void showLoginOutcome(boolean ok, String username);

}

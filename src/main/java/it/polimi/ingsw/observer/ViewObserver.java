package it.polimi.ingsw.observer;

import java.util.Map;

/**
 * Observer interface for views
 */
public interface ViewObserver {
    void onServerInfoInput(Map<String, String> serverInfo);

    void onNicknameInput(String nickname);
}

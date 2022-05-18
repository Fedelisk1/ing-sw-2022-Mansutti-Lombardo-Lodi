package it.polimi.ingsw.observer;

import java.util.Map;

/**
 * Observer interface for views
 */
public interface ViewObserver {
    public void onServerInfoInput(Map<String, String> serverInfo);
}

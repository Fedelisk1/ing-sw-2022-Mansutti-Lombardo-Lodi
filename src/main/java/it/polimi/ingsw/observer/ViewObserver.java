package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.Color;

import java.util.List;
import java.util.Map;

/**
 * Observer interface for views
 */
public interface ViewObserver {
    void onServerInfoInput(Map<String, String> serverInfo);
    void onNicknameInput(String nickname);
    void onNewGameParametersInput(int playersNumber, boolean expertMode);
    void onAssistantCardChosen(int chosenCard);
    void onStudentMovedToIsland(int island, Color color);
    void onStudentMovedToDiningRoom(Color color);
    void onMotherNatureMoved(int steps);
    void onCloudCardChosen(int card);

    void onCCChosen(int card);

    void onCCAllRemoveColorInput(Color color);
}

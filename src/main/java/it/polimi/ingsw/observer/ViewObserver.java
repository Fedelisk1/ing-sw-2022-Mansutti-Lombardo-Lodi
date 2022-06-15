package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Wizard;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Observer interface for views
 */
public interface ViewObserver {
    void onServerInfoInput(Map<String, String> serverInfo);
    void onNicknameInput(String nickname);
    void onNewGameParametersInput(int playersNumber, boolean expertMode);
    void onWizardChosen(Wizard wizard);
    void onAssistantCardChosen(int chosenCard);
    void onStudentMovedToIsland(int island, Color color);
    void onStudentMovedToDiningRoom(Color color);
    void onMotherNatureMoved(int steps);
    void onCloudCardChosen(int card);

    void onCCChosen(int card);

    void onCCAllRemoveColorInput(Color color);
    void onCCBlockColorOnceInput(Color color);
    void onCCChoose1DiningRoomInput(Color color);
    void onCCChose1ToIslandInput(Color color, int island);
    void onCCChoose3ToEntranceInput(EnumMap<Color, Integer> chosenFromCard, EnumMap<Color, Integer> chosenFromEntrance);
    void onCCChoose3ToEntranceSingleInput(Color chosenFromCard, Color chosenFromEntrance, int inputCount);
    void onCCChoose3ToEntranceStop();
    void onCCChooseIslandInput(int chosenIsland);
    void onCCExchange2StudentsInput(EnumMap<Color, Integer> chosenFromEntrance, EnumMap<Color, Integer> chosenFromDiningRoom);
    void onCCExchange2StudentsSingleInput(Color fromEntrance, Color fromDiningRoom, int inputCount);
    void onCCExchange2StudentsStop();
    void onCCNoEntryIslandInput(int chosenIsland);
}

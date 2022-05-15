package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;

public class CloudCard {
    private EnumMap<Color, Integer> students;
    private boolean full;
    private Game currentGame;

    /**
     * fill the card with 3 new students extracted from current game's bag
     */
    public void fillStudents() {
        students = currentGame.extractFromBag(3);
    }

    public boolean isFilled() {
        return students.size() == 3;
    }

    public EnumMap<Color, Integer> getStudents() {
        return students;
    }

    public void setCurrentGame(Game game) {
        currentGame = game;
    }
}



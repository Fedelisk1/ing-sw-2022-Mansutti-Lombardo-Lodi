package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;

public class CloudCard {
    private EnumMap<Color, Integer> students;
    private boolean full;
    private Game currentGame;



    public void fillStudents(EnumMap<Color,Integer> students){

        students = currentGame.extractFromBag(3);

    }

    public void setCurrentGame(Game game) {
    }
}



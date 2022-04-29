package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;

public class CloudCard {
    private EnumMap<Color, Integer> students;
    private boolean full;



    public void fillStudents(EnumMap<Color,Integer> students){

        students = Game.extractFromBag(3);

    }

}



package it.polimi.ingsw.model.charactercards;


import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;
import java.util.EnumMap;

public abstract class CharacterCard {
    protected int cost;
    protected Game currentGame;
    protected String name;
    protected String description;
    protected EnumMap<Color, Integer> students;

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public EnumMap<Color, Integer> getStudents() {
        return students;
    }
}





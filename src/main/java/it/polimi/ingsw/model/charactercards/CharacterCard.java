package it.polimi.ingsw.model.charactercards;


import it.polimi.ingsw.model.Game;

import java.util.ArrayList;
import java.util.EnumMap;

public abstract class CharacterCard {
    public int cost;
    public Game currentGame;


    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }
    public void doEffect(){

    }

}





package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Color;

import java.util.EnumMap;

public interface GameState {
    void startGame();
    void planning1();
    void planning2(int chosenCard);
    void action1Island(Color color, int islandNumber);
    void action1DiningRoom(Color color);
    void action2(int steps);
    void action3(int cloudCard);
    void endPlayerTurn();
}

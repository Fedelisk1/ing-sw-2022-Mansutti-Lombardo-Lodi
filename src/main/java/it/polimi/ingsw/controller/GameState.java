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
    void ccAllRemoveColor(Color color);
    /*void ccBlockColorOnce(Color color);
    void ccBlockTower();
    void ccChoose1DiningRoom(Color color);
    void ccChoose1ToIsland(Color color, int islandNumber);
    void ccChoose3ToEntrance(EnumMap<Color,Integer> chosenFromCard , EnumMap<Color,Integer> chosenFromEntrance);
    void ccChooseIsland(int islandNumber);
    void ccExchange2Students(EnumMap<Color,Integer> chosenFromEntrance,EnumMap<Color,Integer> chosenFromDiningRoom);
    void ccNoEntryIsland(int islandNumber);
    void ccPlus2Influence(int cardPosition);
    void ccTempControlProf();
    void ccTwoAdditionalMoves();*/
}

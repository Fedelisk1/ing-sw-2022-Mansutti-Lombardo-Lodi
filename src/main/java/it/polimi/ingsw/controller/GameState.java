package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.NewGameRequest;

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
    void ccAllRemoveColor(Color color, int cardPosition);
    void ccBlockColorOnce(Color color,int cardPosition);
    void ccBlockTower(int cardPosition);
    void ccChoose1DiningRoom(Color color,int cardPosition);
    void ccChoose1ToIsland(Color color, int islandNumber,int cardPosition);
    void ccChoose3ToEntrance(EnumMap<Color,Integer> chosenFromCard , EnumMap<Color,Integer> chosenFromEntrance,int cardPosition);
    void ccChooseIsland(int islandNumber,int cardPosition);
    void ccExchange2Students(EnumMap<Color,Integer> chosenFromEntrance,EnumMap<Color,Integer> chosenFromDiningRoom,int cardPosition);
    void ccNoEntryIsland(int islandNumber,int cardPosition);
    void ccPlus2Influence(int cardPosition);
    void ccTempControlProf(int cardPosition);
    void ccTwoAdditionalMoves(int cardPosition);




}

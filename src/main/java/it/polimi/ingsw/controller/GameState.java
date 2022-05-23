package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.NewGameRequest;

public interface GameState {
    void startGame();
    void planning1();
    void planning2(int chosenCard);
    void action1Island(Color color, int islandNumber);
    void action1DiningRoom(Color color);
    void action2(int steps);

}

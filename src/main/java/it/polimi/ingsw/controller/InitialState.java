package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.NewGameRequest;

public class InitialState implements GameState{
    private GameController gameController;

    public InitialState(GameController gameController)
    {
        this.gameController=gameController;
    }

    //creates a new game with the number of players and mode specified in the message
    @Override
    public void startGame()
    {
        gameController.changeState(new Planning1State(gameController));
    }

    @Override
    public void planning1() {
        //should never happen
    }

    @Override
    public void planning2(int chosenCard) {

    }

}

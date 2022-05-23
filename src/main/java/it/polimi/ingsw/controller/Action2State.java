package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;

public class Action2State implements GameState{

    private GameController gameController;
    private Game game;

    public Action2State(GameController gameController)
    {
        this.gameController=gameController;
        this.game=gameController.getGame();
    }


    @Override
    public void action2(int steps) {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void planning1() {

    }

    @Override
    public void planning2(int chosenCard) {

    }

    @Override
    public void action1Island(Color color, int islandNumber) {

    }

    @Override
    public void action1DiningRoom(Color color) {

    }
}

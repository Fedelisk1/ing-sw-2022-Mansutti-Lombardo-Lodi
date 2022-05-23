package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;

public class Action3State implements GameState{

    private GameController gameController;
    private Game game;

    public Action3State(GameController gameController)
    {
        this.gameController=gameController;
        this.game=gameController.getGame();
    }


    /**
     * transfers the students from the chosen cloudcard to the entrance of the current player
     * @param cloudCard
     */
    @Override
    public void action3(int cloudCard)
    {
        game.getCloudCards().get(cloudCard).transferStudents();
        gameController.changeState(new EndTurnState(gameController));

    }

    @Override
    public void endPlayerTurn() {

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

    @Override
    public void action2(int steps) {

    }


}

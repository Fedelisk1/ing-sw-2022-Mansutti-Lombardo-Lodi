package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;

public class Action1State implements GameState{
    private GameController gameController;
    private Game game;
    int count;

    public Action1State(GameController gameController)
    {
        count=0;
        this.gameController=gameController;
        game= gameController.getGame();
    }

    /**
     * moves a student of a specified color to a specified island
     * @param color
     * @param islandNumber
     */
    @Override
    public void action1Island(Color color, int islandNumber) {
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().moveToIslandGroup(color, islandNumber);
        count++;

        //if there are 2 players, after 3 times the move method is called, with 3 players 4 times.
        if(count==game.getPlayers().size()+1)
        {
            gameController.changeState(new Action2State(gameController));
        }

    }

    /**
     * moves a student of a specified color to a specified dining room
     * @param color
     */
    @Override
    public void action1DiningRoom(Color color) {

        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().moveStudentToDiningRoom(color);
        count++;

        //if there are 2 players, after 3 times the move method is called, with 3 players 4 times.
        if(count==game.getPlayers().size()+1)
        {
            gameController.changeState(new Action2State(gameController));
        }


    }

    @Override
    public void action2(int steps) {

    }

    @Override
    public void action3(int cloudCard) {

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
}

package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.MissingStudentException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.charactercards.*;
import it.polimi.ingsw.view.VirtualView;

import java.util.EnumMap;

public class Action1State implements GameState{
    private final GameController gameController;
    private final Game game;
    private int movesCount;

    public Action1State(GameController gameController)
    {
        movesCount = 1;
        this.gameController=gameController;
        game= gameController.getGame();
    }

    /**
     * Executes action 1, action 1  must be called a number of times equal to player number to change to next state, it moves students from entrance to dining room or island
     * @param color color of the student to move
     * @param islandNumber 1-indexed index of the destination island. If negative, the student will be moved to the dining room.
     */
    private void action1(Color color, int islandNumber) {
        String currentPlayer = game.getCurrentPlayerNick();
        VirtualView virtualView = gameController.getCurrentPlayerView();

        try {
            if (islandNumber < 0)
                game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().moveStudentToDiningRoom(color);
            else
                game.getCurrentPlayerInstance().getSchoolDashboard().moveToIslandGroup(color, islandNumber - 1);

            gameController.updateViews();
            //gameController.viewsExceptCurrentPlayer().forEach(v -> v.showStringMessage(currentPlayer + " is playing (action phase 1) ..."));

            //if there are 2 players, after 3 times the move method is called, with 3 players 4 times.
            if(movesCount == game.getMaxPlayers() + 1) {
                gameController.changeState(new Action2State(gameController));
                gameController.askActionPhase2();
            } else {
                movesCount++;
                //virtualView.askActionPhase1(movesCount, game.getIslands().size());
                gameController.askActionPhase1();
            }

        } catch (MissingStudentException e) {
            virtualView.showStringMessage("Chosen color is not available in your entrance!");
            virtualView.askActionPhase1(movesCount, game.getIslands().size(), game.isExpertMode());
        }
    }

    /**
     * moves a student of a specified color to a specified island
     * @param color color of the student to move
     * @param islandNumber 1-indexed island number
     */
    @Override
    public void action1Island(Color color, int islandNumber) {
        action1(color, islandNumber);
    }

    /**
     * moves a student of a specified color to a specified dining room
     * @param color color of the student to move
     */
    @Override
    public void action1DiningRoom(Color color) {
        action1(color, -1);
    }

    public int getMovesCount() {
        return movesCount;
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

package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.MissingStudentException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.charactercards.*;

import java.util.EnumMap;

public class Action3State implements GameState{

    private final GameController gameController;
    private final Game game;

    public Action3State(GameController gameController)
    {
        this.gameController=gameController;
        this.game=gameController.getGame();
    }


    /**
     * executes action 3, transfers the students from the chosen cloud card to the entrance of the current player
     * @param cloudCard 1-indexed cloud card number
     */
    @Override
    public void action3(int cloudCard)
    {
        game.getCloudCards().get(cloudCard - 1).transferStudents();
        gameController.updateViews();
        gameController.changeState(new EndTurnState(gameController));
        gameController.getState().endPlayerTurn();
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

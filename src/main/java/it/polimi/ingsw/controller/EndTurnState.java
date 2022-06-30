package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.MissingStudentException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.charactercards.*;

import java.util.EnumMap;

public class EndTurnState implements GameState{

    private final GameController gameController;
    private final Game game;

    public EndTurnState(GameController gameController)
    {
        this.gameController=gameController;
        this.game=gameController.getGame();
    }

    /**
     * executes end player turn and if all player finished their action turn proceed to start again planning phase 1 for the next player,
     * deactivates character card and checks for winning condition
     */
    @Override
    public void endPlayerTurn()
    {
        Player winner;

        //one player finished his action turn
        gameController.addPlayerActionCount();

        //if all player finished their action turns, proceed to the start of the planning phase
        if(gameController.getPlayerActionCount()==game.getPlayers().size())
        {
            //checks for winning condition
            if(game.getCurrentPlayerInstance().getHand().getAssistantCards().size()==0)
            {
                winner = game.winner();

                gameController.notifyWinner(winner);
            }
            //deactivates character cards
            for(Player p: game.getPlayers()){
                p.setCCActivated(false);
            }
            gameController.clearPlayerActionCount();

            //sets next player to the player that played the lowest card value last turn
            game.setCurrentPlayer(game.getPlayers().indexOf(gameController.getPrio().get(0)));
            gameController.getPrio().clear();
            gameController.askAssistantCard(null);
            gameController.changeState(new Planning1State(gameController));
            gameController.getState().planning1();
        }
        //if some players haven't yet completed action turns, set current player to the next player and go back to the start of the action phase
        else {
            //sets current player to the next element in prio arraylist (the next lowest played card value)
            game.setCurrentPlayer(game.getPlayers().indexOf(gameController.getPrio().get(gameController.getPlayerActionCount())));
            gameController.changeState(new Action1State(gameController));
            gameController.askActionPhase1();
        }

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

    @Override
    public void action3(int cloudCard) {

    }
}

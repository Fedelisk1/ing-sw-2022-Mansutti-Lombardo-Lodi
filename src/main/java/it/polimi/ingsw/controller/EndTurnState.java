package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

public class EndTurnState implements GameState{

    private GameController gameController;
    private Game game;

    public EndTurnState(GameController gameController)
    {
        this.gameController=gameController;
        this.game=gameController.getGame();
    }
    @Override
    public void endPlayerTurn()
    {
        Player winner;

        //one player finished his action turn
        gameController.addPlayerActionCount();

        //if all player finished their action turns, proceed to the start of the planning phase
        if(gameController.getPlayerActionCount()==game.getPlayers().size())
        {
            if(game.getCurrentPlayerInstance().getHand().getAssistantCards().size()==0)
            {
                winner=game.winner();
                //TODO: close game
            }
            gameController.clearPlayerActionCount();
            gameController.changeState(new Planning1State(gameController));
        }
        //if some players haven't yet completed action turns, set current player to the next player and go back to the start of the action phase
        else {
            game.setCurrentPlayer((game.getCurrentPlayer()+1)%game.getPlayers().size());
            gameController.changeState(new Action1State(gameController));
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

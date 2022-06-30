package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.reduced.ReducedGame;
import it.polimi.ingsw.network.message.FillCloudCards;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.view.VirtualView;

import java.util.EnumMap;

public class Planning1State implements GameState{
    private final GameController gameController;
    private final Game game;

    public Planning1State(GameController gameController)
    {
        this.gameController=gameController;
        this.game= gameController.getGame();
    }

    /**
     * fills all cloud cards
     */
    @Override
    public void planning1() {
        for(int i=0; i<game.getPlayers().size(); i++)
        {
            game.getCloudCards().get(i).fill();
        }
        gameController.changeState(new Planning2State(gameController));

        gameController.getViews().forEach(vv -> vv.update(new ReducedGame(game)));
    }

    @Override
    public void planning2(int chosenCard) {
        //should never happen
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

    @Override
    public void endPlayerTurn() {

    }



    @Override
    public void startGame()
    {
        //should never happen
    }
}

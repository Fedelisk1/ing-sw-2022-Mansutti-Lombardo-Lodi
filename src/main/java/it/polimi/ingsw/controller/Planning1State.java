package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.message.FillCloudCards;
import it.polimi.ingsw.network.message.Message;

public class Planning1State implements GameState{
    private GameController gameController;
    private Game game;

    public Planning1State(GameController gameController)
    {
        this.gameController=gameController;
        this.game= gameController.getGame();
    }

    @Override
    public void planning1() {
        for(int i=0; i<game.getPlayers().size(); i++)
        {
            game.getCloudCards().get(i).fill();
        }
        gameController.changeState(new Planning2State(gameController));

    }

    @Override
    public void planning2(int chosenCard) {
        //should never happen
    }

    @Override
    public void startGame()
    {
        //should never happen
    }
}

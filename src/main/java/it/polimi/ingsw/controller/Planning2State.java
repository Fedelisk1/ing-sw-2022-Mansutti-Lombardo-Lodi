package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

public class Planning2State implements GameState{

    private GameController gameController;
    private Game game;

    public Planning2State(GameController gameController)
    {
        this.gameController=gameController;
        this.game=gameController.getGame();
    }

    @Override
    public void planning2(int chosenCard)
    {
        game.getPlayers().get(game.getCurrentPlayer()).chooseAssistantCard(chosenCard);

    }



    @Override
    public void startGame() {
        //should never happen
    }

    @Override
    public void planning1() {
        //should never happen
    }
}

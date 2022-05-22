package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

/**
 * Handles the flow of the game serer side
 */
public class GameController {
    private Game game;

    public void gameHandler(Game game)
    {
        this.game=game;
    }


}
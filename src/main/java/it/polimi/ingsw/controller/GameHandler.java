package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

public class GameHandler {
    private Game game;

    public void gameStart(int playerCount)
    {
        game = new Game(playerCount);

        for(int i =0; i<playerCount; i++)
        {
            game.getPlayers().get(i).setCurrentGame(game);
        }

    }
}

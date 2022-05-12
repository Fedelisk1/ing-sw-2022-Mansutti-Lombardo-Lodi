package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.CloudCard;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import javax.swing.text.PlainDocument;

public class GameHandler {
    private Game game;

    public void gameStart(int playerCount)
    {
        game = new Game(playerCount);

        /*
        for (Player p : game.getPlayers())
        {
            p.setCurrentGame(game);
            p.getSchoolDashboard().setCurrentGame(game);
        }
        */
/*
        for (CharacterCard c : game.getCharacterCards()) {
            c.setCurrentGame(game);
        }
*/
        for (CloudCard c : game.getCloudCards()) {
            c.setCurrentGame(game);
        }

    }
}

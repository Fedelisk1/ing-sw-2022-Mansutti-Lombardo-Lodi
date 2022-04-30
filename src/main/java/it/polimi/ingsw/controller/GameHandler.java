package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.CloudCard;
import it.polimi.ingsw.model.Game;

public class GameHandler {
    private Game game;

    public void gameStart(int playerCount)
    {
        game = new Game(playerCount);

        for(int i =0; i<playerCount; i++)
        {
            game.getPlayers().get(i).setCurrentGame(game);
            game.getPlayers().get(i).getSchoolDashboard().setCurrentGame(game);
        }

        for (CharacterCard c : game.getCharacterCards()) {
            c.setCurrentGame(game);
        }

        for (CloudCard c : game.getCloudCards()) {
            c.setCurrentGame(game);
        }
    }
}

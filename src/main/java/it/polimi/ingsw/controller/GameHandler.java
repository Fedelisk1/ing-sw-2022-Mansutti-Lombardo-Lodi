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
        game = new Game(playerCount, true);
    }
}

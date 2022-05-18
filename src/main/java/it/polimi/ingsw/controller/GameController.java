package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.CloudCard;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.View;

import javax.swing.text.PlainDocument;
import java.util.Map;

public class GameController {
    private Game game;
    private View view;

    public void gameHandler(Game game, View view)
    {
        this.game=game;
        this.view=view;
        this.view.addListener(this);
    }


}
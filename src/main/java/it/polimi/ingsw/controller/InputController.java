package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.view.View;

public class InputController {
    private Game game;
    private GameController gameController;
    private View view;
    
    public InputController(Game game, GameController gameController, View view)
    {
        this.game=game;
        this.view=view;
        this.gameController=gameController;
    }
    public boolean verifyInput(Message message)
    {
        switch(message.getMessageType())
        {
            case NEW_GAME_REQUEST:
                return newGameRequest(message);
            case JOIN_GAME_REQUEST:
                return checkUsername(message);
            default:
                return false;
        }

    }

    public boolean newGameRequest(Message message)
    {
        return false;
    }

    /**
     *
     * @param message
     * @return
     */
    public boolean checkUsername(Message message)
    {
        for(int i = 0; i<game.getPlayers().size(); i++)
        {
            if(message.toString().equals(game.getPlayers().get(i).getNickname()))
                return false;
        }
        return true;
    }

}

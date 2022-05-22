package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.NewGameRequest;
import it.polimi.ingsw.network.message.PlayAssistantCard;
import it.polimi.ingsw.view.View;

/**
 * Handles the flow of the game serer side
 */
public class GameController {
    private Game game;
    private View view;
    private GameState state;

    public GameController(Game game, View view)
    {
        this.game=game;
        this.view=view;
        this.state= new InitialState(this);
    }
    public void changeState(GameState state)
    {
        this.state=state;
    }

    public Game getGame() {
        return game;
    }

    public boolean gamePhaseChange(Message message)
    {
        switch(message.getMessageType())
        {
            case START_GAME:
                state.startGame();
            case FILL_CLOUD_CARDS:
                state.planning1();
            case PLAY_ASSISTANT_CARD:
                PlayAssistantCard msg = (PlayAssistantCard) message;
                state.planning2(msg.getChosenCard());

            default:
                return false;
        }

    }

}
package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.view.View;

/**
 * Handles the flow of the game serer side
 */
public class GameController {
    private Game game;
    private View view;
    private GameState state;
    private int playerActionCount;

    public GameController(Game game, View view)
    {
        this.game=game;
        this.view=view;
        this.state= new InitialState(this);
        playerActionCount=0;
    }
    public void changeState(GameState state)
    {
        this.state=state;
    }

    public void startGame()
    {
        state.startGame();
    }

    public Game getGame() {
        return game;
    }

    public void gamePhaseChange(Message message)
    {
        switch(message.getMessageType())
        {
            case FILL_CLOUD_CARDS:
                state.planning1();
            case PLAY_ASSISTANT_CARD:
                PlayAssistantCard msg = (PlayAssistantCard) message;
                state.planning2(msg.getChosenCard());
            case MOVE_STUDENT_TO_ISLAND:
                MoveStudentToIsland msg2 = (MoveStudentToIsland) message;
                state.action1Island(msg2.getColor(),msg2.getIslandNumber());
            case MOVE_STUDENT_TO_DINING_ROOM:
                MoveStudentToDiningRoom msg3 = (MoveStudentToDiningRoom) message;
                state.action1DiningRoom(msg3.getColor());
            case MOVE_MOTHER_NATURE:
                MoveMotherNature msg4 = (MoveMotherNature) message;
                state.action2(msg4.getSteps());
            case CHOOSE_CLOUD_CARD:
                ChooseCloudCard msg5 = (ChooseCloudCard) message;
                state.action3(msg5.getCloudCard());
            case END_PLAYER_TURN:
                state.endPlayerTurn();
        }

    }

    public void clearPlayerActionCount() {
        this.playerActionCount = 0;
    }
    public void addPlayerActionCount() {
        this.playerActionCount++;
    }
    public int getPlayerActionCount() {
        return playerActionCount;
    }

}
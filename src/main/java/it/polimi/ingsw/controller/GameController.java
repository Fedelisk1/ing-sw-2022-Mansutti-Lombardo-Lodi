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
            case CC_ALL_REMOVE_COLOR:
                CCAllRemoveColor msg6 = (CCAllRemoveColor) message;
                state.ccAllRemoveColor(msg6.getColor(), msg6.getCardPosition());
            case CC_BLOCK_COLOR_ONCE:
                CCBlockColorOnce msg7 = (CCBlockColorOnce) message;
                state.ccBlockColorOnce(msg7.getColor(), msg7.getCardPosition());
            case CC_BLOCK_TOWER:
                CCBlockTower msg14 = (CCBlockTower) message;
                state.ccBlockTower(msg14.getCardPosition());
            case CC_CHOOSE_1_DINING_ROOM:
                CCChoose1DiningRoom msg8 = (CCChoose1DiningRoom) message;
                state.ccChoose1DiningRoom(msg8.getColor(),msg8.getCardPosition());
            case CC_CHOOSE_1_TO_ISLAND:
                CCChooseOneToIsland msg9 = (CCChooseOneToIsland) message;
                state.ccChoose1ToIsland(msg9.getColor(),msg9.getIslandNumber(), msg9.getCardPosition());
            case CC_CHOOSE_3_TO_ENTRANCE:
                CCChoose3ToEntrance msg10 = (CCChoose3ToEntrance) message;
                state.ccChoose3ToEntrance(msg10.getChosenFromCard(),msg10.getChosenFromEntrance(), msg10.getCardPosition());
            case CC_CHOOSE_ISLAND:
                CCChooseIsland msg11 = (CCChooseIsland) message;
                state.ccChooseIsland(msg11.getIslnumb(), msg11.getCardPosition());
            case CC_EXCHANGE_2_STUDENTS:
                CCExchange2Students msg12 = (CCExchange2Students) message;
                state.ccExchange2Students(msg12.getChosenFromEntrance(),msg12.getChosenFromDiningRoom(), msg12.getCardPosition());
            case CC_NO_ENTRY_ISLAND:
                CCNoEntryIsland msg13 = (CCNoEntryIsland) message;
                state.ccNoEntryIsland(msg13.getIslNumb(), msg13.getCardPosition());
            case CC_PLUS_2_INFLUENCE:
                CCPlus2Influence msg15 = (CCPlus2Influence) message;
                state.ccPlus2Influence(msg15.getCardPosition());
            case CC_TEMP_CONTROL_PROF:
                CCTempControlProf msg16 = (CCTempControlProf) message;
                state.ccTempControlProf(msg16.getCardPosition());
            case CC_TWO_ADDITIONAL_MOVES:
                CCTwoAdditionalMoves msg17 = (CCTwoAdditionalMoves) message;
                state.ccTwoAdditionalMoves(msg17.getCardPosition());
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
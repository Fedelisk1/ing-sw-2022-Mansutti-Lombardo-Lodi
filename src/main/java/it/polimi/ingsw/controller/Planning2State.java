package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.EnumMap;

public class Planning2State implements GameState{

    private GameController gameController;
    private Game game;
    int count;

    public Planning2State(GameController gameController)
    {
        this.gameController=gameController;
        this.game=gameController.getGame();
        count = 0;
    }

    /**
     * executes planning phase 2, planning phase 2 must be called a number of times equal to player number to change to next state
     * @param chosenCard by the player
     */
    @Override
    public void planning2(int chosenCard)
    {
        //the current player chooses the assistant card, then the current player is changed to the next
        game.getPlayers().get(game.getCurrentPlayer()).chooseAssistantCard(chosenCard);
        if(game.getCurrentPlayer()+1==game.getPlayers().size())
        {
            game.setCurrentPlayer(0);

        }
        else
        {
            game.setCurrentPlayer(game.getCurrentPlayer()+1);
        }
        count++;

        //if all players have chosen an assistant card, it sets the current player to the player that has chosen the card
        //with the least value and changes state
        if(count==game.getPlayers().size())
        {
            int position=0;
            for(int i=1;i<game.getPlayers().size();i++)
            {
                if(game.getPlayers().get(i).getCardValue()<game.getPlayers().get(position).getCardValue())
                {
                    position=i;
                }
            }
            game.setCurrentPlayer(position);
            gameController.changeState(new Action1State(gameController));
        }


    }


    @Override
    public void action1Island(Color color, int islandNumber) {

    }

    @Override
    public void action1DiningRoom(Color color) {

    }

    @Override
    public void action2(int steps) {

    }

    @Override
    public void action3(int cloudCard) {

    }

    @Override
    public void endPlayerTurn() {

    }

    @Override
    public void ccAllRemoveColor(Color color, int cardPosition) {

    }

    @Override
    public void ccBlockColorOnce(Color color, int cardPosition) {

    }

    @Override
    public void ccBlockTower(int cardPosition) {

    }

    @Override
    public void ccChoose1DiningRoom(Color color, int cardPosition) {

    }

    @Override
    public void ccChoose1ToIsland(Color color, int islandNumber, int cardPosition) {

    }

    @Override
    public void ccChoose3ToEntrance(EnumMap<Color, Integer> chosenFromCard, EnumMap<Color, Integer> chosenFromEntrance, int cardPosition) {

    }

    @Override
    public void ccChooseIsland(int islandNumber, int cardPosition) {

    }

    @Override
    public void ccExchange2Students(EnumMap<Color, Integer> chosenFromEntrance, EnumMap<Color, Integer> chosenFromDiningRoom, int cardPosition) {

    }

    @Override
    public void ccNoEntryIsland(int islandNumber, int cardPosition) {

    }

    @Override
    public void ccPlus2Influence(int cardPosition) {

    }

    @Override
    public void ccTempControlProf(int cardPosition) {

    }

    @Override
    public void ccTwoAdditionalMoves(int cardPosition) {

    }


    @Override
    public void startGame() {
        //should never happen
    }

    @Override
    public void planning1() {
        //should never happen
    }
}

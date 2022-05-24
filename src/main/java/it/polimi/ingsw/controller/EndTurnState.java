package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.EnumMap;

public class EndTurnState implements GameState{

    private GameController gameController;
    private Game game;

    public EndTurnState(GameController gameController)
    {
        this.gameController=gameController;
        this.game=gameController.getGame();
    }
    @Override
    public void endPlayerTurn()
    {
        Player winner;

        //one player finished his action turn
        gameController.addPlayerActionCount();

        //if all player finished their action turns, proceed to the start of the planning phase
        if(gameController.getPlayerActionCount()==game.getPlayers().size())
        {
            if(game.getCurrentPlayerInstance().getHand().getAssistantCards().size()==0)
            {
                winner=game.winner();
                //TODO: close game
            }
            gameController.clearPlayerActionCount();
            gameController.changeState(new Planning1State(gameController));
        }
        //if some players haven't yet completed action turns, set current player to the next player and go back to the start of the action phase
        else {
            game.setCurrentPlayer((game.getCurrentPlayer()+1)%game.getPlayers().size());
            gameController.changeState(new Action1State(gameController));
        }

    }
    @Override
    public void ccAllRemoveColor(Color color, int cardPosition)
    {
        AllRemoveColor chosenCard = (AllRemoveColor) game.getCharacterCards().get(cardPosition);
        chosenCard.doEffect(color);
    }
    @Override
    public void ccBlockColorOnce(Color color,int cardPosition)
    {
        BlockColorOnce chosenCard = (BlockColorOnce) game.getCharacterCards().get(cardPosition);
        chosenCard.doEffect(color);
    }
    @Override
    public void ccBlockTower(int cardPosition)
    {
        BlockTower chosenCard = (BlockTower) game.getCharacterCards().get(cardPosition);
        chosenCard.doEffect();
    }
    @Override
    public void ccChoose1DiningRoom(Color color,int cardPosition)
    {
        Choose1DiningRoom chosenCard = (Choose1DiningRoom) game.getCharacterCards().get(cardPosition);
        chosenCard.doEffect(color);
    }
    @Override
    public void ccChoose1ToIsland(Color color, int islandNumber,int cardPosition)
    {
        Choose1ToIsland chosenCard = (Choose1ToIsland) game.getCharacterCards().get(cardPosition);
        chosenCard.doEffect(color, islandNumber);
    }
    @Override
    public void ccChoose3ToEntrance(EnumMap<Color,Integer> chosenFromCard , EnumMap<Color,Integer> chosenFromEntrance, int cardPosition)
    {
        Choose3toEntrance chosenCard = (Choose3toEntrance) game.getCharacterCards().get(cardPosition);
        chosenCard.doEffect(chosenFromCard,chosenFromEntrance);
    }
    @Override
    public void ccChooseIsland(int islandNumber,int cardPosition)
    {
        ChooseIsland chosenCard = (ChooseIsland) game.getCharacterCards().get(cardPosition);
        chosenCard.doEffect(islandNumber);
    }
    @Override
    public void ccExchange2Students(EnumMap<Color,Integer> chosenFromEntrance,EnumMap<Color,Integer> chosenFromDiningRoom,int cardPosition)
    {
        Exchange2Students chosenCard = (Exchange2Students) game.getCharacterCards().get(cardPosition);
        chosenCard.doEffect(chosenFromEntrance,chosenFromDiningRoom);
    }
    @Override
    public void ccNoEntryIsland(int islandNumber,int cardPosition)
    {
        NoEntryIsland chosenCard = (NoEntryIsland) game.getCharacterCards().get(cardPosition);
        chosenCard.doEffect(islandNumber);
    }
    @Override
    public void ccPlus2Influence(int cardPosition)
    {
        Plus2Influence chosenCard = (Plus2Influence) game.getCharacterCards().get(cardPosition);
        chosenCard.doEffect();
    }
    @Override
    public void ccTempControlProf(int cardPosition)
    {
        TempControlProf chosenCard = (TempControlProf) game.getCharacterCards().get(cardPosition);
        chosenCard.doEffect();
    }
    @Override
    public void ccTwoAdditionalMoves(int cardPosition)
    {
        TwoAdditionalMoves chosenCard = (TwoAdditionalMoves) game.getCharacterCards().get(cardPosition);
        chosenCard.doEffect();
    }

    @Override
    public void startGame() {

    }

    @Override
    public void planning1() {

    }

    @Override
    public void planning2(int chosenCard) {

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
}

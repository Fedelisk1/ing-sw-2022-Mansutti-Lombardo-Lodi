package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.*;

import java.util.EnumMap;

public class Action1State implements GameState{
    private GameController gameController;
    private Game game;
    int count;

    public Action1State(GameController gameController)
    {
        count=0;
        this.gameController=gameController;
        game= gameController.getGame();
    }

    /**
     * moves a student of a specified color to a specified island
     * @param color
     * @param islandNumber
     */
    @Override
    public void action1Island(Color color, int islandNumber) {
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().moveToIslandGroup(color, islandNumber);
        count++;

        //if there are 2 players, after 3 times the move method is called, with 3 players 4 times.
        if(count==game.getPlayers().size()+1)
        {
            gameController.changeState(new Action2State(gameController));
        }

    }

    /**
     * moves a student of a specified color to a specified dining room
     * @param color
     */
    @Override
    public void action1DiningRoom(Color color) {

        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().moveStudentToDiningRoom(color);
        count++;

        //if there are 2 players, after 3 times the move method is called, with 3 players 4 times.
        if(count==game.getPlayers().size()+1)
        {
            gameController.changeState(new Action2State(gameController));
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
    public void action2(int steps) {

    }

    @Override
    public void action3(int cloudCard) {

    }

    @Override
    public void endPlayerTurn() {

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
}

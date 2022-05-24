package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.message.FillCloudCards;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.model.Color;

import java.util.EnumMap;

public class Planning1State implements GameState{
    private GameController gameController;
    private Game game;

    public Planning1State(GameController gameController)
    {
        this.gameController=gameController;
        this.game= gameController.getGame();
    }

    /**
     * fills all cloud cards
     */
    @Override
    public void planning1() {
        for(int i=0; i<game.getPlayers().size(); i++)
        {
            game.getCloudCards().get(i).fill();
        }
        gameController.changeState(new Planning2State(gameController));

    }

    @Override
    public void planning2(int chosenCard) {
        //should never happen
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
    public void startGame()
    {
        //should never happen
    }
}

package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.reduced.ReducedGame;
import it.polimi.ingsw.network.message.Update;
import it.polimi.ingsw.view.VirtualView;

import java.util.*;

import java.awt.*;
import java.util.List;

public class Planning2State implements GameState{

    private final GameController gameController;
    private final Game game;
    private int count;
    private final List<Integer> playedAssistants;

    public Planning2State(GameController gameController)
    {
        this.gameController=gameController;
        this.game=gameController.getGame();
        count = 0;

        playedAssistants = new ArrayList<>();
    }

    /**
     * executes planning phase 2, planning phase 2 must be called a number of times equal to player number to change to next state
     * @param chosenPriority by the player
     */
    @Override
    public void planning2(int chosenPriority)
    {
        //the current player chooses the assistant card, then the current player is changed to the next
        game.getCurrentPlayerInstance().chooseAssistantCard(chosenPriority);

        gameController.viewsExceptCurrentPlayer()
                .forEach(vv -> vv.showPlayedAssistantCard(game.getCurrentPlayerNick(), chosenPriority));

        playedAssistants.add(chosenPriority);

        if(count == game.getMaxPlayers() - 1) {

            //creates a copy of the player array
            for (int i =0; i<game.getPlayers().size(); i++)
            {
                gameController.getPrio().add(game.getPlayers().get(i));
            }
            //sorts the array based on card value through mergesort algorithm
            gameController.getPrio().sort(Comparator.comparing(Player::getCardValue));



            //sets the current player to the first element of the prio list (player with lowest card value)
            game.setCurrentPlayer(game.getPlayers().indexOf(gameController.getPrio().get(0)));
            playedAssistants.clear();
            gameController.changeState(new Action1State(gameController));


            String currentNick = game.getCurrentPlayerNick();
            gameController.getCurrentPlayerView().update(new ReducedGame(game));
            gameController.askActionPhase1();

        }
        else {
            game.setCurrentPlayer((game.getCurrentPlayer() + 1) % game.getMaxPlayers());

            // in case the player has only cards that have already been played, let him play anyway
            if (playedAssistants.equals(game.getCurrentPlayerInstance().getHand().getAssistantCardsAsList()))
                gameController.askAssistantCard(null);
            else
                gameController.askAssistantCard(playedAssistants);

        }

        //if all players have chosen an assistant card, it sets the current player to the player that has chosen the card
        //with the least value and changes state

        count++;
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
    public void ccAllRemoveColor(Color color) {

    }
//
//    @Override
//    public void ccBlockColorOnce(Color color, int cardPosition) {
//
//    }
//
//    @Override
//    public void ccBlockTower(int cardPosition) {
//
//    }
//
//    @Override
//    public void ccChoose1DiningRoom(Color color, int cardPosition) {
//
//    }
//
//    @Override
//    public void ccChoose1ToIsland(Color color, int islandNumber, int cardPosition) {
//
//    }
//
//    @Override
//    public void ccChoose3ToEntrance(EnumMap<Color, Integer> chosenFromCard, EnumMap<Color, Integer> chosenFromEntrance, int cardPosition) {
//
//    }
//
//    @Override
//    public void ccChooseIsland(int islandNumber, int cardPosition) {
//
//    }
//
//    @Override
//    public void ccExchange2Students(EnumMap<Color, Integer> chosenFromEntrance, EnumMap<Color, Integer> chosenFromDiningRoom, int cardPosition) {
//
//    }
//
//    @Override
//    public void ccNoEntryIsland(int islandNumber, int cardPosition) {
//
//    }
//
//    @Override
//    public void ccPlus2Influence(int cardPosition) {
//
//    }
//
//    @Override
//    public void ccTempControlProf(int cardPosition) {
//
//    }
//
//    @Override
//    public void ccTwoAdditionalMoves(int cardPosition) {
//
//    }


    @Override
    public void startGame() {
        //should never happen
    }

    @Override
    public void planning1() {
        //should never happen
    }
}

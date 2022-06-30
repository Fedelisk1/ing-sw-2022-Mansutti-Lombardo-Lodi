package it.polimi.ingsw.controller;
import it.polimi.ingsw.exceptions.MissingStudentException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.charactercards.*;

import java.sql.SQLOutput;
import java.util.EnumMap;

public class Action2State implements GameState{

    private final GameController gameController;
    private final Game game;

    public Action2State(GameController gameController)
    {
        this.gameController=gameController;
        this.game=gameController.getGame();
    }


    /**
     * Moves mother nature, changes island ownership and checks/executes possible island merges
     * @param steps to move mother nature
     */
    @Override
    public void action2(int steps) {
        Player winner = null;

        //moves mother nature
        game.moveMotherNature(steps);

        //if the island is already occupied, add back the towers to the occupying player, otherwise just remove towers from the winning player
        IslandGroup currentIslandGroup = game.getMotherNatureIsland();
        int islandIndex = game.getMotherNaturePosition();

        //calculates player with the highest influence and sets occupation on island and adds/removes towers
        int groupSize = currentIslandGroup.getIslandCount();
        Player higherInfluence = game.playerWithHigherInfluence(currentIslandGroup);

        //if winning player is tied, go to next state
        if(higherInfluence == null) {
            gameController.log("Player with higher influence is tied");
            gameController.updateViews();
            gameController.askActionPhase3();
            gameController.changeState(new Action3State(gameController));
            return;
        }


        //if no one occupies the island, remove towers from the winning player dashboard
        if(currentIslandGroup.getOccupiedBy() == null) {
            gameController.log("player with higher influence is " + higherInfluence.getNickname());
            higherInfluence.getSchoolDashboard().removeTowers(groupSize);
            currentIslandGroup.setOccupiedBy(higherInfluence);
        } else if(! higherInfluence.equals(currentIslandGroup.getOccupiedBy())) {
            //if the island is already occupied, add towers back to the losing player and remove towers from the winning player

            System.out.println(higherInfluence.getNickname() + " steals cotrol of the island to" + currentIslandGroup.getOccupiedBy().getNickname());
            currentIslandGroup.getOccupiedBy().getSchoolDashboard().addTowers(groupSize);
            higherInfluence.getSchoolDashboard().removeTowers(groupSize);
            currentIslandGroup.setOccupiedBy(higherInfluence);
        }

        //game ends if one player finishes the towers
        if(higherInfluence.getSchoolDashboard().getTowers()<=0) {
            winner = higherInfluence;

            gameController.notifyWinner(winner);
            return;
        }

        //checks if the next island group in the sequence is occupied by the same player
        if(game.getNextIsland().getOccupiedBy() == higherInfluence)
            game.mergeIslands(game.getMotherNaturePosition());
        //also, if the previous island group is occupied by the same player
        if(game.getPreviousIsland().getOccupiedBy() == higherInfluence) {
            game.mergeIslands(game.getPreviousMotherNaturePosition());
        }

        //if there are 3 or less island groups left, game ends.
        if(game.getIslands().size()<=3)
        {
            winner = game.winner();

            gameController.notifyWinner(winner);
            return;
        }

        gameController.updateViews();
        gameController.askActionPhase3();
        gameController.changeState(new Action3State(gameController));

    }
    @Override
    public void ccAllRemoveColor(Color color)
    {
        AllRemoveColor card = (AllRemoveColor) game.getCharacterCard(CharacterCardType.ALL_REMOVE_COLOR);
        card.doEffect(color);
    }
//    @Override
//    public void ccBlockColorOnce(Color color,int cardPosition)
//    {
//        BlockColorOnce chosenCard = (BlockColorOnce) game.getCharacterCards().get(cardPosition);
//        chosenCard.doEffect(color);
//    }
//    @Override
//    public void ccBlockTower(int cardPosition)
//    {
//        BlockTower chosenCard = (BlockTower) game.getCharacterCards().get(cardPosition);
//        chosenCard.doEffect();
//    }
//    @Override
//    public void ccChoose1DiningRoom(Color color,int cardPosition)
//    {
//        Choose1DiningRoom chosenCard = (Choose1DiningRoom) game.getCharacterCards().get(cardPosition);
//        chosenCard.doEffect(color);
//    }
//    @Override
//    public void ccChoose1ToIsland(Color color, int islandNumber,int cardPosition)
//    {
//        Choose1ToIsland chosenCard = (Choose1ToIsland) game.getCharacterCards().get(cardPosition);
//        chosenCard.doEffect(color, islandNumber);
//    }
//    @Override
//    public void ccChoose3ToEntrance(EnumMap<Color,Integer> chosenFromCard , EnumMap<Color,Integer> chosenFromEntrance, int cardPosition)
//    {
//        Choose3toEntrance chosenCard = (Choose3toEntrance) game.getCharacterCards().get(cardPosition);
//        try {
//            chosenCard.doEffect(chosenFromCard,chosenFromEntrance);
//        } catch (MissingStudentException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    @Override
//    public void ccChooseIsland(int islandNumber,int cardPosition)
//    {
//        ChooseIsland chosenCard = (ChooseIsland) game.getCharacterCards().get(cardPosition);
//        chosenCard.doEffect(islandNumber);
//    }
//    @Override
//    public void ccExchange2Students(EnumMap<Color,Integer> chosenFromEntrance,EnumMap<Color,Integer> chosenFromDiningRoom,int cardPosition)
//    {
//        Exchange2Students chosenCard = (Exchange2Students) game.getCharacterCards().get(cardPosition);
//        try {
//            chosenCard.doEffect(chosenFromEntrance,chosenFromDiningRoom);
//        } catch (MissingStudentException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    @Override
//    public void ccNoEntryIsland(int islandNumber,int cardPosition)
//    {
//        NoEntryIsland chosenCard = (NoEntryIsland) game.getCharacterCards().get(cardPosition);
//        chosenCard.doEffect(islandNumber);
//    }
//    @Override
//    public void ccPlus2Influence(int cardPosition)
//    {
//        Plus2Influence chosenCard = (Plus2Influence) game.getCharacterCards().get(cardPosition);
//        chosenCard.doEffect();
//    }
//    @Override
//    public void ccTempControlProf(int cardPosition)
//    {
//        TempControlProf chosenCard = (TempControlProf) game.getCharacterCards().get(cardPosition);
//        chosenCard.doEffect();
//    }
//    @Override
//    public void ccTwoAdditionalMoves(int cardPosition)
//    {
//        TwoAdditionalMoves chosenCard = (TwoAdditionalMoves) game.getCharacterCards().get(cardPosition);
//        chosenCard.doEffect();
//    }


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

    @Override
    public void action1Island(Color color, int islandNumber) {

    }

    @Override
    public void action1DiningRoom(Color color) {

    }
}

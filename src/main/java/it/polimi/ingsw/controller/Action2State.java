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

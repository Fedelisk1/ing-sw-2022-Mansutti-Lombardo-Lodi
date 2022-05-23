package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.IslandGroup;
import it.polimi.ingsw.model.Player;

public class Action2State implements GameState{

    private GameController gameController;
    private Game game;

    public Action2State(GameController gameController)
    {
        this.gameController=gameController;
        this.game=gameController.getGame();
    }


    @Override
    public void action2(int steps) {

        //moves mother nature
        game.moveMotherNature(steps);

        //calculates player with highest influence and sets occupation on island
        IslandGroup currentIslandGroup = game.getMotherNatureIsland();
        Player winningPlayer= game.playerWithHigherInfluence(currentIslandGroup);
        currentIslandGroup.setOccupiedBy(winningPlayer);

        //towers are automatically set on island upon ownership, all is needed is to remove towers from the school dashboard.
        int groupSize = currentIslandGroup.getIslandCount();
        game.getCurrentPlayerInstance().getSchoolDashboard().removeTowers(groupSize);

        //checks if the next island in the sequence is occupied by the same player
        if(game.getIslands().get(game.getMotherNaturePosition()+1).getOccupiedBy()==winningPlayer)
        {
            game.mergeIslands(game.getMotherNaturePosition());
        }

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

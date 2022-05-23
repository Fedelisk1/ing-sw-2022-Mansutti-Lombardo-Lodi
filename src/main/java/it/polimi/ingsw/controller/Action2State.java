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


        //calculates player with highest influence and sets occupation on island and adds/removes towers
        int groupSize = currentIslandGroup.getIslandCount();
        Player winningPlayer= game.playerWithHigherInfluence(currentIslandGroup);

        //if winning player is tied, go to next state
        if(winningPlayer==null)
        {
            gameController.changeState(new Action3State(gameController));
            return;
        }


        //if no one occupies the island, remove towers from the winning player dashboard
        if(currentIslandGroup.getOccupiedBy()==null)
        {
            winningPlayer.getSchoolDashboard().removeTowers(groupSize);
            currentIslandGroup.setOccupiedBy(winningPlayer);
        }
        //if the island is already occupied, add towers back to the losing player and remove towers from the winning player
        else
        {
            if(!winningPlayer.equals(currentIslandGroup.getOccupiedBy()))
            {
                currentIslandGroup.getOccupiedBy().getSchoolDashboard().addTowers(groupSize);
                winningPlayer.getSchoolDashboard().removeTowers(groupSize);
                currentIslandGroup.setOccupiedBy(winningPlayer);
            }
        }

        if(winningPlayer.getSchoolDashboard().getTowers()<=0)
        {
            winner = winningPlayer;
            //TODO: close game
        }

        //checks if the next island group in the sequence is occupied by the same player
        if(game.getIslands().get((game.getMotherNaturePosition()+1)%game.getIslands().size()).getOccupiedBy()==winningPlayer)
            game.mergeIslands(game.getMotherNaturePosition());
        //also, if the previous island group is occupied by the same player
        if(game.getIslands().get(Math.floorMod((game.getMotherNaturePosition()-1),game.getIslands().size())).getOccupiedBy()==winningPlayer)
            game.mergeIslands(game.getMotherNaturePosition()-1);

        //if there are 3 or less island groups left, game ends.
        if(game.getIslands().size()<=3)
        {
            winner = game.winner();
            //TODO: close game
        }

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

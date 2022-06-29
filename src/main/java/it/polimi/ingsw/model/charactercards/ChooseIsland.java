package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.Game;

public class ChooseIsland extends CharacterCard {
    public ChooseIsland(Game currentGame) {
        this.currentGame = currentGame;
        cost = 3;

        type = CharacterCardType.CHOOSE_ISLAND;
        name = "ChooseIsland";
        description = "Choose an Island and resolve the Island as if Mother Nature had ended her movement there. Mother Nature will still move and the Island where she ends her movement will also be resolved.";
    }

    /**
     * Check if the attribute occupiedBy is different from null ,and then check if the influence is major than the player and eventually
     * place the tower, else place the tower and set occupiedBy with the currentPlayer
     *
     * @param islandNumber index of the island on which apply the effect.
     */

    public void doEffect(int islandNumber) {

        currentGame.getPlayers().get(currentGame.getCurrentPlayer()).setCoins(currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getCoins() - cost);

        cost = 4;

        //if the island is occupied by a different player than the current
        if (currentGame.getIslands().get(islandNumber).getOccupiedBy() != null &&
                currentGame.getIslands().get(islandNumber).getOccupiedBy() != currentGame.getPlayers().get(currentGame.getCurrentPlayer())) {
            //if the count of influence of the current player is major than the influence of the player who owns the island
            if (currentGame.countInfluence(currentGame.getPlayers().get(currentGame.getCurrentPlayer()), currentGame.getIslands().get(islandNumber)) >
                    currentGame.countInfluence(currentGame.getIslands().get(islandNumber).getOccupiedBy(), currentGame.getIslands().get(islandNumber))) {
                //sets the occupiedby to the current player, removes tower from current player, adda a tower to the old owner
                int sizeIsland = currentGame.getIslands().get(islandNumber).getIslandCount();
                currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getSchoolDashboard().removeTowers(sizeIsland);
                currentGame.getIslands().get(islandNumber).getOccupiedBy().getSchoolDashboard().addTowers(sizeIsland);
                currentGame.getIslands().get(islandNumber).setOccupiedBy(currentGame.getPlayers().get(currentGame.getCurrentPlayer()));
            }
            //if no one occupies the island
        } else if (currentGame.getIslands().get(islandNumber).getOccupiedBy() == null) {
            //decrements the number of towers and sets occupied by
            int sizeIsland = currentGame.getIslands().get(islandNumber).getIslandCount();
            currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getSchoolDashboard().removeTowers(sizeIsland);
            currentGame.getIslands().get(islandNumber).setOccupiedBy(currentGame.getPlayers().get(currentGame.getCurrentPlayer()));

        }

    }
}

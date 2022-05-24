package it.polimi.ingsw.model;

public class BlockTower extends CharacterCard {
    public BlockTower() {
        cost = 3;
    }

    /**
     * Set true the boolean blockTower of IslandGroup
     */
    public void doEffect() {

        currentGame.getPlayers().get(currentGame.getCurrentPlayer()).setCoins(currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getCoins() - cost);

        cost = 4;
        currentGame.getIslands().get(currentGame.getMotherNaturePosition()).setBlockTower_CC(true);
    }
}

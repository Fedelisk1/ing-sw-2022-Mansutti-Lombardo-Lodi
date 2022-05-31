package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.Game;

public class BlockTower extends CharacterCard {
    public BlockTower(Game currentGame) {
        this.currentGame = currentGame;
        cost = 3;

        name = "BlockTower";
        description = "When resolving a Conquering on an island, Towers do not count towards influence.";
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

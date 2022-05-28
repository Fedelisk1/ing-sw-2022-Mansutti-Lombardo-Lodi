package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.Color;

public class BlockColorOnce extends CharacterCard {
    public BlockColorOnce() {
        cost = 3;
    }

    /**
     * Set true the boolean blockColorOnce of IslandGroup and set the color to block
     *
     * @param c is the color to block
     */
    public void doEffect(Color c) {
        currentGame.getPlayers().get(currentGame.getCurrentPlayer()).setCoins(currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getCoins() - cost);

        cost = 4;
        currentGame.getIslands().get(currentGame.getMotherNaturePosition()).setBlockedColor(c);
        currentGame.getIslands().get(currentGame.getMotherNaturePosition()).setBlockColorOnce_CC(true);
    }
}

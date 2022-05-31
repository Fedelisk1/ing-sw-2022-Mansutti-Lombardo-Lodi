package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.Game;

public class Plus2Influence extends CharacterCard {
    public Plus2Influence(Game currentGame) {
        this.currentGame = currentGame;
        cost = 2;

        name = "Plus2Influence";
        description = "During the influence calculation this turn, you count as having 2 more influence";
    }

    /**
     * Set true the boolean plus2Influence of IslandGroup
     */
    public void doEffect() {

        currentGame.getPlayers().get(currentGame.getCurrentPlayer()).setCoins(currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getCoins() - cost);

        cost = 3;
        currentGame.getIslands().get(currentGame.getMotherNaturePosition()).setPlus2Influence_CC(true);
    }


}

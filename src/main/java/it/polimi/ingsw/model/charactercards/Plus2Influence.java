package it.polimi.ingsw.model.charactercards;

public class Plus2Influence extends CharacterCard {
    public Plus2Influence() {
        cost = 2;
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

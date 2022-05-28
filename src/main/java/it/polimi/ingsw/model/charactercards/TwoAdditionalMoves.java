package it.polimi.ingsw.model.charactercards;

public class TwoAdditionalMoves extends CharacterCard {
    public TwoAdditionalMoves() {
        cost = 1;
    }

    /**
     * Add 2 to the attribute maxPosition of player
     */

    public void doEffect() {
        currentGame.getPlayers().get(currentGame.getCurrentPlayer()).setCoins(currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getCoins() - cost);

        cost = 2;
        currentGame.getPlayers().get(currentGame.getCurrentPlayer()).setMaxSteps(currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getMaxSteps() + 2);
    }
}

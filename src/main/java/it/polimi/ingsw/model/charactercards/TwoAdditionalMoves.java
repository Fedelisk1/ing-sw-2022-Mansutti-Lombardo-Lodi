package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.Game;

public class TwoAdditionalMoves extends CharacterCard {
    public TwoAdditionalMoves(Game currentGame) {
        this.currentGame = currentGame;
        cost = 1;

        name = "TwoAdditionalMoves";
        description = "You may move Mother Nature up to 2 additional Islands than is indicated by the Assistant card you've played.";
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

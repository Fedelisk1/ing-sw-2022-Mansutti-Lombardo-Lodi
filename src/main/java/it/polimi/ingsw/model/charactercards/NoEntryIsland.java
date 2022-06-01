package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.Game;

public class NoEntryIsland extends CharacterCard {
    private int availableUses;

    public NoEntryIsland(Game currentGame) {
        this.currentGame = currentGame;
        availableUses = 4;
        cost = 2;

        type = CharacterCardType.NO_ENTRY_ISLAND;
        name = "NoEntryIsland";
        description = "Place a No Entry tile on an Island of you choice. The first time Mother Nature ends her movement there, put the No Entry tile back onto this card. Do not calculate influence on that island, or place any towers.";
    }

    /**
     * Set true the boolean noEntryIsland of IslandGroup and decrement availableUses
     */
    public void doEffect(int islNumb) {

        currentGame.getPlayers().get(currentGame.getCurrentPlayer()).setCoins(currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getCoins() - cost);

        cost = 3;

        if (availableUses > 0) {

            availableUses = availableUses - 1;
            currentGame.getIslands().get(islNumb).setNoEntryIsland(true);


        } else throw new IllegalArgumentException("Maximum number of effect uses");

    }

    public int getAvailableUses() {
        return availableUses;
    }
}

package it.polimi.ingsw.model.charactercards;

public class NoEntryIsland extends CharacterCard {
    int availableUses;

    public NoEntryIsland() {
        availableUses = 4;
        cost = 2;
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

}

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

        //se l'isola è occupata da un giocatore diverso dal corrente
        if (currentGame.getIslands().get(islandNumber).getOccupiedBy() != null &&
                currentGame.getIslands().get(islandNumber).getOccupiedBy() != currentGame.getPlayers().get(currentGame.getCurrentPlayer())) {
            //se il calcolo dell'influenza del giocatore corrente sull'isola è maggiore dell'influenza del giocatore che detiene l'isola
            if (currentGame.countInfluence(currentGame.getPlayers().get(currentGame.getCurrentPlayer()), currentGame.getIslands().get(islandNumber)) >
                    currentGame.countInfluence(currentGame.getIslands().get(islandNumber).getOccupiedBy(), currentGame.getIslands().get(islandNumber))) {
                //assegni l'occupiedby al giocatore corrente, decrementi il conto delle torri del giocatore corrente e incrementi quello del vecchio occupato
                int sizeIsland = currentGame.getIslands().get(islandNumber).getIslandCount();
                currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getSchoolDashboard().removeTowers(sizeIsland);
                currentGame.getIslands().get(islandNumber).getOccupiedBy().getSchoolDashboard().addTowers(sizeIsland);
                currentGame.getIslands().get(islandNumber).setOccupiedBy(currentGame.getPlayers().get(currentGame.getCurrentPlayer()));
            }
            //se l'isola non è occupata da nessuno
        } else if (currentGame.getIslands().get(islandNumber).getOccupiedBy() == null) {
            //decrementare le torri e settare occupiedby
            int sizeIsland = currentGame.getIslands().get(islandNumber).getIslandCount();
            currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getSchoolDashboard().removeTowers(sizeIsland);
            currentGame.getIslands().get(islandNumber).setOccupiedBy(currentGame.getPlayers().get(currentGame.getCurrentPlayer()));

        }

    }
}

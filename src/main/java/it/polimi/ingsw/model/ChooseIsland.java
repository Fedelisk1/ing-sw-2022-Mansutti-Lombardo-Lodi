package it.polimi.ingsw.model;

public class ChooseIsland extends CharacterCard {
    public ChooseIsland() {
        cost = 3;
    }

    /**
     * Check if the attribute occupiedBy is different from null ,and then check if the influence is major than the player and eventually
     * place the tower,else place the tower and set occupiedBy with the currentPlayer
     *
     * @param islnumb
     */

    public void doEffect(int islnumb) {

        currentGame.getPlayers().get(currentGame.getCurrentPlayer()).setCoins(currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getCoins() - cost);

        cost = 4;
        //se l'isola è occupata da un giocatore diverso dal corrente
        if (currentGame.getIslands().get(islnumb).getOccupiedBy() != null &&
                currentGame.getIslands().get(islnumb).getOccupiedBy() != currentGame.getPlayers().get(currentGame.getCurrentPlayer())) {
            //se il calcolo dell'influenza del giocatore corrente sull'isola è maggiore dell'influenza del giocatore che detiene l'isola
            if (currentGame.countInfluence(currentGame.getPlayers().get(currentGame.getCurrentPlayer()), currentGame.getIslands().get(islnumb)) >
                    currentGame.countInfluence(currentGame.getIslands().get(islnumb).getOccupiedBy(), currentGame.getIslands().get(islnumb))) {
                //assegni l'occupiedby al giocatore corrente, decrementi il conto delle torri del giocatore corrente e incrementi quello del vecchio occupato
                int sizeIsland = currentGame.getIslands().get(islnumb).getIslandCount();
                currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getSchoolDashboard().removeTowers(sizeIsland);
                currentGame.getIslands().get(islnumb).getOccupiedBy().getSchoolDashboard().addTowers(sizeIsland);
                currentGame.getIslands().get(islnumb).setOccupiedBy(currentGame.getPlayers().get(currentGame.getCurrentPlayer()));
            }
            //se l'isola non è occupata da nessuno
        } else if (currentGame.getIslands().get(islnumb).getOccupiedBy() == null) {
            //decrementare le torri e settare occupiedby
            int sizeIsland = currentGame.getIslands().get(islnumb).getIslandCount();
            currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getSchoolDashboard().removeTowers(sizeIsland);
            currentGame.getIslands().get(islnumb).setOccupiedBy(currentGame.getPlayers().get(currentGame.getCurrentPlayer()));

        }

    }
}

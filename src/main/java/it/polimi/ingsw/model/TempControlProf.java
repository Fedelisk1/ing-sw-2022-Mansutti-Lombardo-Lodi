package it.polimi.ingsw.model;

import java.util.ArrayList;

public class TempControlProf extends CharacterCard {
    private int cost;
    private ArrayList<Player> playerModified;

    public TempControlProf() {
        cost = 2;
        playerModified = new ArrayList<>();
    }

    /**
     * If the player who activated the card has an equal number of students in the dining room then the professor is added
     * to the current player and removed from the 'other
     */

    public void doEffect() {

        currentGame.getPlayers().get(currentGame.getCurrentPlayer()).setCoins(currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getCoins() - cost);


        cost = 3;

        for (Player p : currentGame.getPlayers()) {

            if (p != currentGame.getPlayers().get(currentGame.getCurrentPlayer())) {

                for (Color c : Color.values()) {

                    if (currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getSchoolDashboard().getDiningRoom().get(c) == p.getSchoolDashboard().getDiningRoom().get(c) && p.getSchoolDashboard().getDiningRoom().get(c) != 0) {
                        if (p.getSchoolDashboard().getProfessors().contains(c)) {

                            p.getSchoolDashboard().removeProfessor(c);
                            currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getSchoolDashboard().addProfessor(c);
                            playerModified.add(p);
                        }


                    }

                }
            }

        }


    }

    /**
     * When the turn ends, this method is called to bring back to the starting condition
     */
    public void resetTempControlProf() {
        for (Player p : playerModified) {

            for (Color c : Color.values()) {

                if (currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getSchoolDashboard().getDiningRoom().get(c) == p.getSchoolDashboard().getDiningRoom().get(c) && p.getSchoolDashboard().getDiningRoom().get(c) != 0) {
                    currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getSchoolDashboard().removeProfessor(c);
                    p.getSchoolDashboard().addProfessor(c);
                }
            }
        }
        playerModified.clear();
    }

    public ArrayList<Player> getPlayerModified() {
        return playerModified;
    }
}

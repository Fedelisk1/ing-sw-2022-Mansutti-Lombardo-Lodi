package it.polimi.ingsw.model;

import java.util.EnumMap;

public class Exchange2Students extends CharacterCard {
    public Exchange2Students() {
        cost = 1;
    }

    /**
     * This method allows you to exchange two students from the entrance to the dining room
     *
     * @param chosenFromEntrance   are the cards chosen from the entrance
     * @param chosenFromDiningRoom are the cards chosen from the dining room
     */
    public void doEffect(EnumMap<Color, Integer> chosenFromEntrance, EnumMap<Color, Integer> chosenFromDiningRoom) {

        currentGame.getPlayers().get(currentGame.getCurrentPlayer()).setCoins(currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getCoins() - cost);

        cost = 2;

        for (Color c : chosenFromEntrance.keySet()) {
            while (chosenFromEntrance.get(c) > 0) {

                chosenFromEntrance.put(c, chosenFromEntrance.get(c) - 1);
                currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getSchoolDashboard().addStudentToDiningRoom(c);
                currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getSchoolDashboard().removeStudentFromEntrance(c);

            }
        }

        for (Color c : chosenFromDiningRoom.keySet()) {
            while (chosenFromDiningRoom.get(c) > 0) {
                chosenFromDiningRoom.put(c, chosenFromDiningRoom.get(c) - 1);
                currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getSchoolDashboard().addStudentToEntrance(c);
                currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getSchoolDashboard().removeStudentFromDiningRoom(c);
            }
        }

    }


}

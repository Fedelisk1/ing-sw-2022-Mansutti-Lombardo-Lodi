package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.exceptions.MissingStudentException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.SchoolDashboard;

import java.util.EnumMap;

public class Exchange2Students extends CharacterCard {
    public Exchange2Students(Game currentGame) {
        this.currentGame = currentGame;
        cost = 1;

        type = CharacterCardType.EXCHANGE_2_STUDENTS;
        name = "Exchange2Students";
        description = "You may exchange up to 2 Students between your Entrance and your Dining Room";
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

    public void doPartialEffect(Color fromEntrance, Color fromDiningRoom) {
        SchoolDashboard sd = currentGame.getCurrentPlayerInstance().getSchoolDashboard();
        cost = 2;

        // remove from entrance
        sd.getEntrance().merge(fromEntrance, -1, Integer::sum);

        // add to dining room
        sd.getDiningRoom().merge(fromEntrance, 1, Integer::sum);

        // remove from dining room
        sd.getDiningRoom().merge(fromDiningRoom, -1, Integer::sum);

        // add to entrance
        sd.getEntrance().merge(fromDiningRoom, 1, Integer::sum);
    }
}

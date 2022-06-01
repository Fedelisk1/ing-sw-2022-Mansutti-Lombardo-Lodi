package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;

import java.util.EnumMap;

public class Choose1DiningRoom extends CharacterCard {

    public Choose1DiningRoom(Game currentGame) {
        this.currentGame = currentGame;
        cost = 2;
        students = new EnumMap<>(Color.class);

        type = CharacterCardType.CHOOSE_1_DINING_ROOM;
        name = "Choose1DiningRoom";
        description = "Take 1 Student from this card and place it in your Dining Room. Then, draw a new Student from the Bag and place it on this card.";

        init();
    }

    public void init() {
        students = currentGame.extractFromBag(4);
    }

    public EnumMap<Color, Integer> getStudents() {
        return students;
    }

    /**
     * From the 4 students on the card, one is chosen and placed in the dining room
     *
     * @param c color of the student
     */
    public void doEffect(Color c) {

        currentGame.getCurrentPlayerInstance().setCoins(currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getCoins() - cost);

        if (!students.containsKey(c)) throw new IllegalArgumentException("Not present");

        cost = 3;
        currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getSchoolDashboard().addStudentToDiningRoom(c);
        students.put(c, students.get(c) - 1);

        EnumMap<Color, Integer> replacementStudent = currentGame.extractFromBag(1);
        //extracted.merge(currentGame.extractFromBag(), 1, Integer::sum);

        for (Color x : replacementStudent.keySet()) {
            if (!students.containsKey(x))
                students.put(x, 1);
            else students.put(x, students.get(x) + 1);

            replacementStudent.remove(x);
        }

    }
}

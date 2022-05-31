package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;

import java.util.EnumMap;

public class Choose1ToIsland extends CharacterCard {

    public Choose1ToIsland(Game currentGame) {
        this.currentGame = currentGame;
        students = new EnumMap<>(Color.class);

        cost = 1;

        name = "Choose1ToIsland";
        description = "Take 1 Student from this card and place it on an island of your choice. Then, draw a student from the Bag and place it on this card.";

        init();
    }

    public void init() {
        students = currentGame.extractFromBag(4);
    }

    /**
     * From 4 students on the card, 1 is chosen and placed on the island
     *
     * @param c            is a color
     * @param islandNumber the number of the island where the student will be placed
     */

    public void doEffect(Color c, int islandNumber) {
        EnumMap<Color, Integer> extractedFromBag;

        currentGame.getPlayers().get(currentGame.getCurrentPlayer()).setCoins(currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getCoins() - cost);

        cost = 2;
        if (!students.containsKey(c)) throw new IllegalArgumentException("Not present");
        else {
            students.put(c, students.get(c) - 1);

            currentGame.getIslands().get(islandNumber).addStudents(c);

            extractedFromBag = currentGame.extractFromBag(1);

            for (Color x : extractedFromBag.keySet()) {
                if (!students.containsKey(x))
                    students.put(x, 1);
                else
                    students.put(x, students.get(x) + 1);

                extractedFromBag.remove(x);
            }
        }
    }

    public void setExtracted(EnumMap<Color, Integer> extracted) {
        this.students = extracted;
    }

    public EnumMap<Color, Integer> getExtracted() {
        return students;
    }
}

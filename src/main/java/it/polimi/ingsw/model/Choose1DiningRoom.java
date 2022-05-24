package it.polimi.ingsw.model;

import java.util.EnumMap;

public class Choose1DiningRoom extends CharacterCard {
    private EnumMap<Color, Integer> extracted;
    private EnumMap<Color, Integer> extractedFromBag;

    public Choose1DiningRoom() {
        cost = 2;
        extracted = new EnumMap<>(Color.class);
        extractedFromBag = new EnumMap<>(Color.class);


    }

    public void init() {
        extracted = currentGame.extractFromBag(4);
    }

    public EnumMap<Color, Integer> getExtracted() {
        return extracted;
    }

    /**
     * From the 4 students on the card, one is chosen and placed in the dining room
     *
     * @param c color of the student
     */
    public void doEffect(Color c) {
        currentGame.getPlayers().get(currentGame.getCurrentPlayer()).setCoins(currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getCoins() - cost);

        if (!extracted.containsKey(c)) throw new IllegalArgumentException("Not present");

        cost = 3;
        currentGame.getPlayers().get(currentGame.getCurrentPlayer()).getSchoolDashboard().addStudentToDiningRoom(c);
        extracted.put(c, extracted.get(c) - 1);

        extractedFromBag = currentGame.extractFromBag(1);

        for (Color x : extractedFromBag.keySet()) {
            if (!extracted.containsKey(x))
                extracted.put(x, 1);
            else extracted.put(x, extracted.get(x) + 1);

            extractedFromBag.remove(x);
        }

    }
}

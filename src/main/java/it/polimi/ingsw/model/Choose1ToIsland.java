package it.polimi.ingsw.model;

import java.util.EnumMap;

public class Choose1ToIsland extends CharacterCard {

    private EnumMap<Color, Integer> extracted;
    private boolean used;
    private int cost;


    public Choose1ToIsland() {
        extracted = new EnumMap<>(Color.class);

        cost = 1;
    }

    public void init() {
        extracted = currentGame.extractFromBag(4);
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
        if (!extracted.containsKey(c)) throw new IllegalArgumentException("Not present");
        else {
            extracted.put(c, extracted.get(c) - 1);

            currentGame.getIslands().get(islandNumber).addStudents(c);

            extractedFromBag = currentGame.extractFromBag(1);

            for (Color x : extractedFromBag.keySet()) {
                if (!extracted.containsKey(x))
                    extracted.put(x, 1);
                else
                    extracted.put(x, extracted.get(x) + 1);

                extractedFromBag.remove(x);
            }
        }
    }

    public void setExtracted(EnumMap<Color, Integer> extracted) {
        this.extracted = extracted;
    }

    public EnumMap<Color, Integer> getExtracted() {
        return extracted;
    }
}

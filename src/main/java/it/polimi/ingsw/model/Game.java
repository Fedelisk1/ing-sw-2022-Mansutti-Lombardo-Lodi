package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;

public class Game {
    private EnumMap<Color, Integer> bag;
    private int motherNaturePosition;
    private final ArrayList<IslandGroup> islands;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private ArrayList<CharacterCard> characterCards;
    private int totalCoins;
    private ArrayList<CloudCard> cloudCards;
    private ArrayList<Color> unudesProfessors;

    public Game(int players) {
        islands = new ArrayList<>();

        for(int i = 0; i < 12; i++)
            islands.add(new IslandGroup());
    }

    public ArrayList<IslandGroup> getIslands() {
        return islands;
    }

    /**
     * merges the island in the given index with the next one
     * @param index index of the first island to merge
     * @throws IndexOutOfBoundsException when the given index exceets the maximum index
     */
    public void mergeIslands(int index) throws IndexOutOfBoundsException {
        IslandGroup first = islands.get(index);
        IslandGroup second;

        try {
            second = islands.get(index + 1);
        } catch (IndexOutOfBoundsException ex) {
            second = islands.get(0);
            index = 0;
        }

        IslandGroup merged = buildUnifiedIsland(first, second);

        islands.remove(first);
        islands.remove(second);

        islands.add(index, merged);
    }

    /**
     * returns a new island which is the merge of the two given
     * @param first first island to merge
     * @param second second island to merge
     * @return unified island
     */
    private IslandGroup buildUnifiedIsland(IslandGroup first, IslandGroup second) {
        IslandGroup result = new IslandGroup();

        for(Color c : Color.values()) {
            result.addStudents(c, first.getStudents(c) + second.getStudents(c));
        }

        result.incrementIslandCount(first.getIslandCount());
        result.incrementIslandCount(second.getIslandCount());

        return result;
    }

}

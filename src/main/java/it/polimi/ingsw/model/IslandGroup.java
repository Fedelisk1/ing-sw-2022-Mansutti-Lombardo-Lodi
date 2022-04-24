package it.polimi.ingsw.model;

import java.util.EnumMap;

public class IslandGroup {
    private int islandCount = 1;
    private Player occupiedBy;
    private final boolean noEntryTile = false;
    private final EnumMap<Color, Integer> students = new EnumMap<>(Color.class);

    /**
     * Island constructor. Initializes the island with 0 students for each color
     */
    public IslandGroup() {
        for (Color c : Color.values())
            students.put(c, 0);
    }

    public IslandGroup(int islandCount) {
        this.islandCount = islandCount;
    }

    /**
     * increases the number of students of the given color by the given quantity
     * @param color color of the students to add
     * @param quantity quantity by which increase students
     */
    public void addStudents(Color color, int quantity) {
        int prevVal = students.get(color) == null ? 0 : students.get(color);
        students.put(color, prevVal + quantity);
    }

    public void addStudents(Color color) {
        addStudents(color, 1);
    }

    public int getStudents(Color color) {
        return students.get(color);
    }

    public int getIslandCount() {
        return islandCount;
    }

    public void incrementIslandCount(int quantity) {
        islandCount += quantity;
    }

    public void incrementIslandCount() {
        incrementIslandCount(1);
    }
}

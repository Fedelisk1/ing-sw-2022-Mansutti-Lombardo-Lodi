package it.polimi.ingsw.model;

import java.util.EnumMap;

public class IslandGroup {
    private int islandCount = 1;
    private Player occupiedBy;
    private final boolean noEntryTile = false;
    private final EnumMap<Color, Integer> students = new EnumMap<>(Color.class);
    private boolean blockColorOnce_CC=false;
    private boolean plus2Influence_CC=false;
    private boolean blockTower_CC=false;
    private boolean noEntryIsland=false;

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

    public EnumMap<Color,Integer> getStudents(){return students;}

    public int getIslandCount() {
        return islandCount;
    }

    public void incrementIslandCount(int quantity) {
        islandCount += quantity;
    }

    public void incrementIslandCount() {incrementIslandCount(1);}

    public Player getOccupiedBy() {return occupiedBy;}

    public boolean isBlockColorOnce_CC() {
        return blockColorOnce_CC;
    }

    public boolean isPlus2Influence_CC() {
        return plus2Influence_CC;
    }

    public boolean isBlockTower_CC() {
        return blockTower_CC;
    }

    public boolean isNoEntryIsland() {
        return noEntryIsland;
    }

    public void setBlockColorOnce_CC(boolean blockColorOnce_CC) {
        this.blockColorOnce_CC = blockColorOnce_CC;
    }

    public void setPlus2Influence_CC(boolean plus2Influence_CC) {
        this.plus2Influence_CC = plus2Influence_CC;
    }

    public void setBlockTower_CC(boolean blockTower_CC) {
        this.blockTower_CC = blockTower_CC;
    }

    public void setNoEntryIsland(boolean noEntryIsland) {
        this.noEntryIsland = noEntryIsland;
    }
}

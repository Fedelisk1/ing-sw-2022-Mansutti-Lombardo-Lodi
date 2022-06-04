package it.polimi.ingsw.model.reduced;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.IslandGroup;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

public class ReducedIsland implements Serializable {
    @Serial
    private static final long serialVersionUID = 7908305170018052276L;
    private final Map<Color, Integer> students;
    private final int towers;
    private final String occupierNick;
    private final boolean motherNature;
    private final int noEntryTiles;

    public ReducedIsland(Game game, IslandGroup islandGroup) {
        if (! game.getIslands().contains(islandGroup))
            throw new IllegalArgumentException("island must be part of given game");

        this.students = islandGroup.getStudents();

        if (islandGroup.getOccupiedBy() != null)
            this.occupierNick = islandGroup.getOccupiedBy().getNickname();
        else
            this.occupierNick = null;

        this.towers = islandGroup.getIslandCount();
        this.motherNature = game.getMotherNatureIsland().equals(islandGroup);
        this.noEntryTiles = islandGroup.getNoEntryTiles();
    }

    public Map<Color, Integer> getStudents() {
        return students;
    }

    public int getTowers() {
        return towers;
    }

    public String getOccupierNick() {
        return occupierNick;
    }

    public boolean isMotherNature() {
        return motherNature;
    }

    public int getNoEntryTiles() {
        return noEntryTiles;
    }
}

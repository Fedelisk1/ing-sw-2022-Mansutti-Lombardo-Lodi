package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private final Game game2Players = new Game(2);

    @Test
    public void testMergeIslands_index0() {
        Game g = new Game(2);
        ArrayList<IslandGroup> beforeMerge2To11 = null;
        ArrayList<IslandGroup> afterMerge1To10 = null;

        try {
            beforeMerge2To11 = new ArrayList<>(g.getIslands().subList(2, 12));
        } catch (IndexOutOfBoundsException e) {
            fail();
        }

        assertEquals(12, g.getIslands().size());

        g.mergeIslands(0);

        try {
            afterMerge1To10 = new ArrayList<>(g.getIslands().subList(1, 11));
        } catch (IndexOutOfBoundsException ex) {
            fail();
        }

        assertEquals(11, g.getIslands().size());
        assertEquals(beforeMerge2To11, afterMerge1To10);
    }

    @Test
    public void testMergeIslands_index11() {
        Game g = new Game(2);
        ArrayList<IslandGroup> beforeMerge1To10 = null;
        ArrayList<IslandGroup> afterMerge1To10 = null;

        try {
            beforeMerge1To10 = new ArrayList<>(g.getIslands().subList(1, 11));
        } catch (IndexOutOfBoundsException e) {
            fail();
        }

        assertEquals(12, g.getIslands().size());

        g.mergeIslands(11);

        try {
            afterMerge1To10 = new ArrayList<>(g.getIslands().subList(1, 11));
        } catch (IndexOutOfBoundsException ex) {
            fail();
        }

        assertEquals(11, g.getIslands().size());
        assertEquals(beforeMerge1To10, afterMerge1To10);
    }


}
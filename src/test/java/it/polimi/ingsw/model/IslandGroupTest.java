package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IslandGroupTest {
    private Game g;
    @BeforeEach
    public void init() {
        g = new Game(2, true);
    }

    @Test
    public void testIslandGroup() {
        IslandGroup ig = new IslandGroup(new Game(2, false));

        ig.addStudents(Color.BLUE, 4);
        ig.addStudents(Color.RED, 2);
        ig.addStudents(Color.GREEN, 1);

        assertEquals(4, ig.getStudents(Color.BLUE));
        assertEquals(2, ig.getStudents(Color.RED));
        assertEquals(1, ig.getStudents(Color.GREEN));
    }

    @Test
    public void testGetPreviousIsland() {
        IslandGroup first = g.getIslands().get(0);
        IslandGroup current = g.getCurrentIsland();
        IslandGroup last = g.getIslands().get(11);

        assertEquals(first, current);
        assertEquals(last, g.getPreviousIsland());
    }
}
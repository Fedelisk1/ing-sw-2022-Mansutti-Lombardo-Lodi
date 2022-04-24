package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IslandGroupTest {

    @Test
    public void testIslandGroup() {
        IslandGroup ig = new IslandGroup();

        ig.addStudents(Color.BLUE, 4);
        ig.addStudents(Color.RED, 2);
        ig.addStudents(Color.GREEN, 1);

        assertEquals(4, ig.getStudents(Color.BLUE));
        assertEquals(2, ig.getStudents(Color.RED));
        assertEquals(1, ig.getStudents(Color.GREEN));
    }
}
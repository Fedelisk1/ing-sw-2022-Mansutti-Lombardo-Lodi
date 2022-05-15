package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game g;

    @BeforeEach
    public void init() {
        g = new Game(2, true);
    }

    @Test
    public void testMergeIslands_index0() {
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

    @Test
    public void testMoveMN() {
        assertEquals(0, g.getMotherNaturePosition());
        g.moveMotherNature(3);
        assertEquals(3, g.getMotherNaturePosition());
        g.moveMotherNature(12);
        assertEquals(3, g.getMotherNaturePosition());
        g.moveMotherNature(9);
        assertEquals(0, g.getMotherNaturePosition());
    }

    @Test
    public void testExtractFromBag() {
        int prevStudentsInBag = g.studentsInBag();
        EnumMap<Color, Integer> s = g.extractFromBag(3);
        int sum = 0;
        for(Color c : s.keySet())
            sum += s.get(c);
        assertEquals(3, sum);
        assertEquals(prevStudentsInBag - 3, g.studentsInBag());
    }

    @Test
    public void testExtractFromBagWithoutArgs() {
        int prevStudentsInBag = g.studentsInBag();
        Color color = g.extractFromBag();
        assertEquals(prevStudentsInBag - 1, g.studentsInBag());
    }

    @Test
    public void testAddToBag() {
        int prevStudentsInBag = g.studentsInBag();
        EnumMap<Color, Integer> toAdd = new EnumMap<Color, Integer>(Color.class);
        toAdd.put(Color.GREEN, 1);
        toAdd.put(Color.YELLOW, 2);
        toAdd.put(Color.BLUE, 3);
        g.addToBag(toAdd);
        assertEquals(prevStudentsInBag + 6, g.studentsInBag());
    }

    @Test
    public void testCountInfluence() {
        IslandGroup islandGroup = g.getIslands().get(0);

        g.getPlayers().get(0).getSchoolDashboard().addStudentsToEntrance(Color.BLUE, 2);
        g.getPlayers().get(0).getSchoolDashboard().addStudentsToEntrance(Color.YELLOW, 2);
        g.getPlayers().get(1).getSchoolDashboard().addStudentsToEntrance(Color.GREEN, 3);

        g.getPlayers().get(0).getSchoolDashboard().moveToIslandGroup(Color.BLUE, 0);
        g.getPlayers().get(0).getSchoolDashboard().moveToIslandGroup(Color.BLUE, 0);
        g.getPlayers().get(0).getSchoolDashboard().moveToIslandGroup(Color.YELLOW, 0);
        g.getPlayers().get(0).getSchoolDashboard().moveToIslandGroup(Color.YELLOW, 0);
        g.getPlayers().get(1).getSchoolDashboard().moveToIslandGroup(Color.GREEN, 0);
        g.getPlayers().get(1).getSchoolDashboard().moveToIslandGroup(Color.GREEN, 0);
        g.getPlayers().get(1).getSchoolDashboard().moveToIslandGroup(Color.GREEN, 0);

        System.out.println(g.countInfluence(g.getPlayers().get(0), g.getIslands().get(0)));
        System.out.println(g.countInfluence(g.getPlayers().get(1), g.getIslands().get(0)));
    }
}
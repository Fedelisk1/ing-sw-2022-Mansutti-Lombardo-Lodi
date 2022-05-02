package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchoolDashboardTest
{
    @Test
    public void RemoveTowersTest()
    {
        Game game = new Game(3);
        game.getPlayers().get(1).getSchoolDashboard().removeTowers(3);
        Assertions.assertEquals(5, game.getPlayers().get(1).getSchoolDashboard().getTowers());
    }

    @Test
    public void RemoveTowersShouldThrowRuntimeExceptionWhenInputIsNegativeTest()
    {
        Game game = new Game(3);
        game.getPlayers().get(1).getSchoolDashboard().removeTowers(3);
        Assertions.assertThrows(IllegalArgumentException.class, () ->{ game.getPlayers().get(1).getSchoolDashboard().removeTowers(-3);});
    }
    @Test
    public void RemoveTowersShouldThrowRuntimeExceptionWhenLessThan0Test()
    {
        Game game = new Game(3);
        game.getPlayers().get(1).getSchoolDashboard().removeTowers(3);
        Assertions.assertThrows(IllegalArgumentException.class, () ->{ game.getPlayers().get(1).getSchoolDashboard().removeTowers(9);});
    }

    @Test
    public void addTowersTest()
    {
        Game game = new Game(3);
        game.getPlayers().get(1).getSchoolDashboard().removeTowers(3);
        game.getPlayers().get(1).getSchoolDashboard().addTowers(1);
        Assertions.assertEquals(6, game.getPlayers().get(1).getSchoolDashboard().getTowers());
    }

    @Test
    public void addTowersShouldThrowRuntimeExceptionWhenMoreThan8Test()
    {
        Game game = new Game(3);
        Assertions.assertThrows(IllegalArgumentException.class, () ->{ game.getPlayers().get(1).getSchoolDashboard().addTowers(1);});
    }

    @Test
    public void addTowersShouldThrowRuntimeExceptionWhenNegativeTest()
    {
        Game game = new Game(3);
        Assertions.assertThrows(IllegalArgumentException.class, () ->{ game.getPlayers().get(1).getSchoolDashboard().addTowers(-1);});
    }

    @Test
    public void addStudentToEntranceTest()
    {
        Game game = new Game(3);
        game.getPlayers().get(1).getSchoolDashboard().addStudentToEntrance(Color.YELLOW);
        Assertions.assertEquals(1, game.getPlayers().get(1).getSchoolDashboard().getEntrance().get(Color.YELLOW));
    }
    @Test
    public void addStudentToEntranceShouldThrowRuntimeExceptionWhenNullTest()
    {
        Game game = new Game(3);
        Assertions.assertThrows(NullPointerException.class, () -> {game.getPlayers().get(1).getSchoolDashboard().addStudentToEntrance(null);});
    }

    @Test
    public void removeStudentFromEntranceTest()
    {
        Game game = new Game(3);
        game.getPlayers().get(1).getSchoolDashboard().addStudentToEntrance(Color.YELLOW);
        game.getPlayers().get(1).getSchoolDashboard().addStudentToEntrance(Color.RED);
        game.getPlayers().get(1).getSchoolDashboard().addStudentToEntrance(Color.RED);
        game.getPlayers().get(1).getSchoolDashboard().removeStudentFromEntrance(Color.YELLOW);
        game.getPlayers().get(1).getSchoolDashboard().removeStudentFromEntrance(Color.RED);
        Assertions.assertEquals(0, game.getPlayers().get(1).getSchoolDashboard().getEntrance().get(Color.YELLOW));
        Assertions.assertEquals(1, game.getPlayers().get(1).getSchoolDashboard().getEntrance().get(Color.RED));
    }

    @Test
    public void removeStudentFromEntranceShouldThrowRuntimeExceptionWhenNullTest()
    {
        Game game = new Game(3);
        Assertions.assertThrows(NullPointerException.class, () -> {game.getPlayers().get(1).getSchoolDashboard().removeStudentFromEntrance(null);});
    }



}
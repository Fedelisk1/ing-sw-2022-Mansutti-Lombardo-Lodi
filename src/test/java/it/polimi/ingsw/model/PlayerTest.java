package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Game game;
    @BeforeEach
    public void setUp()
    {
        game = new Game(3, true);
    }

    @Test
    public void chooseAssistantCardTest()
    {
        game.getPlayers().get(game.getCurrentPlayer()).chooseAssistantCard(0);

        Assertions.assertEquals(1,game.getPlayers().get(game.getCurrentPlayer()).getMaxSteps());
        Assertions.assertEquals(1,game.getPlayers().get(game.getCurrentPlayer()).getCardValue());

    }


    @Test
    public void chooseCloudCardTest()
    {
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().clear();
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().putIfAbsent(Color.GREEN,0);
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().putIfAbsent(Color.RED,0);
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().putIfAbsent(Color.PINK,0);
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().putIfAbsent(Color.BLUE,0);
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().putIfAbsent(Color.YELLOW,0);


        int greenStudents = game.getCloudCards().get(1).getStudents().get(Color.GREEN);
        int redStudents = game.getCloudCards().get(1).getStudents().get(Color.RED);
        int blueStudents = game.getCloudCards().get(1).getStudents().get(Color.BLUE);
        int yellowStudents = game.getCloudCards().get(1).getStudents().get(Color.YELLOW);
        int pinkStudents = game.getCloudCards().get(1).getStudents().get(Color.PINK);

        game.getPlayers().get(game.getCurrentPlayer()).chooseCloudCard(1);

        Assertions.assertEquals(greenStudents, game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().get(Color.GREEN));
        Assertions.assertEquals(redStudents, game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().get(Color.RED));
        Assertions.assertEquals(blueStudents, game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().get(Color.BLUE));
        Assertions.assertEquals(yellowStudents, game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().get(Color.YELLOW));
        Assertions.assertEquals(pinkStudents, game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().get(Color.PINK));



    }






}
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
    public void moveOneOfThreeToIsland()
    {
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().clear();
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().addStudentToEntrance(Color.GREEN);
        game.getPlayers().get(game.getCurrentPlayer()).moveOneOfThreeToIsland(Color.GREEN,0);

    }




}
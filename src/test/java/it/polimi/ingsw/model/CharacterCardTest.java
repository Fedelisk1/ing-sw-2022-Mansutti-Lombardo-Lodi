package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCardTest {

}


class Choose1ToIslandTest{
    @Test

    public void testDoEffect(){


        Game game = new Game(2);
        Choose1ToIsland p=new Choose1ToIsland();
        p.doEffect(Color.YELLOW,2);


    }

}
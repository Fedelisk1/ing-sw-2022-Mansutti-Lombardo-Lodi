package it.polimi.ingsw.model;



import it.polimi.ingsw.exceptions.MissingStudentException;
import it.polimi.ingsw.model.charactercards.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCardTest {



}

class Choose1ToIslandTest{
    @Test

    public void testDoEffect(){
        Game game = new Game(2, true);
        game.addPlayer("p1");
        game.addPlayer("p2");

        Choose1ToIsland p = new Choose1ToIsland(game);

        EnumMap<Color,Integer> extractedFromCard= new EnumMap<>(Color.class);
        extractedFromCard.put(Color.BLUE,2);
        extractedFromCard.put(Color.YELLOW,1);
        extractedFromCard.put(Color.GREEN,1);
        p.setExtracted(extractedFromCard);

        game.getPlayers().get(game.getCurrentPlayer()).setCoins(2);

        //controlliamo se sono 4 gli studenti di extracted
        int sum=0;
        for(Color c : p.getExtracted().keySet()){
            sum= sum+ p.getExtracted().get(c);
        }
        assertEquals(4,sum);

        // se ne sceglie 1
        Color choosen;
        choosen= Color.GREEN;

        //si sceglie l'isola in cui metterlo
        int isl=1;
        //metto in x il numero di studenti del colore choosen prima del do effect
        int x= game.getIslands().get(isl).getStudents().get(choosen);


        p.doEffect(choosen, isl);
        //vediamo se è stato veramente aggiunto lo studente all'isola
        assertEquals(1 + x, game.getIslands().get(isl).getStudents().get(choosen));

        sum=0;
        for(Color c : p.getExtracted().keySet()){
            sum= sum+ p.getExtracted().get(c);
        }
        assertEquals(4,sum);

    }

    @Test
    public void testInit(){
        Game game = new Game(2, true);
        Choose1ToIsland p = new Choose1ToIsland(game);

        p.init();

        int sum=0;
        for(Color c : p.getExtracted().keySet()){
            sum= sum+ p.getExtracted().get(c);
        }
        assertEquals(4,sum);

    }


}




class TwoAdditionalMovesTest {
    @Test
    public void testDoEffect() {
        Game game = new Game(2, true);
        game.addPlayer("p1");
        game.addPlayer("p2");

        TwoAdditionalMoves p= new TwoAdditionalMoves(game);

        game.getPlayers().get(game.getCurrentPlayer()).setCoins(3);
        int x= game.getPlayers().get(game.getCurrentPlayer()).getMaxSteps();
        p.doEffect();
        assertEquals(x+2,game.getPlayers().get(game.getCurrentPlayer()).getMaxSteps());
    }

}
class NoEntryIslandTest{
    @Test
    public void testDoEffect(){
        Game game = new Game(2, true);
        game.addPlayer("p1");
        game.addPlayer("p2");

        NoEntryIsland p=new NoEntryIsland(game);

        game.getPlayers().get(game.getCurrentPlayer()).setCoins(3);
        int islNumb=1;
        game.getIslands().get(islNumb).addStudents(Color.GREEN,2);
        game.getCurrentPlayerInstance().getSchoolDashboard().addStudentToDiningRoom(Color.GREEN);

        assertEquals(2,p.getCost());

        assertEquals(0,game.getIslands().get(islNumb).getNoEntryTiles());
        p.doEffect(islNumb);
        assertEquals(1,game.getIslands().get(islNumb).getNoEntryTiles());
        assertEquals(0,game.countInfluence(game.getCurrentPlayerInstance(),game.getIslands().get(islNumb)));
        game.playerWithHigherInfluence(game.getIslands().get(islNumb));
        assertEquals(0,game.getIslands().get(islNumb).getNoEntryTiles());
        assertEquals(3,p.getNoEntryTiles());
        assertEquals(3,p.getCost());

    }
}

class BlockTowerTest{
    @Test
    public void testDoEffect(){
        Game game = new Game(2, true);
        game.addPlayer("p1");
        game.addPlayer("p2");
        game.setCurrentPlayer(0);

        BlockTower p=new BlockTower(game);

        game.getPlayers().get(game.getCurrentPlayer()).setCoins(3);
        game.getIslands().get(0).addStudents(Color.GREEN,2);
        game.getCurrentPlayerInstance().getSchoolDashboard().addStudentToDiningRoom(Color.GREEN);
        game.getIslands().get(0).setOccupiedBy(game.getCurrentPlayerInstance());

        assertEquals(3,p.getCost());

        assertEquals(false,game.isBlockTower());
        assertEquals(3,game.countInfluence(game.getPlayers().get(0),game.getIslands().get(0)));

        p.doEffect();

        assertEquals(true,game.isBlockTower());

        assertEquals(2,game.countInfluence(game.getCurrentPlayerInstance(),game.getIslands().get(0)));

        assertEquals(game.getPlayers().get(0),game.playerWithHigherInfluence(game.getIslands().get(0)));
        assertEquals(false,game.isBlockTower());
        assertEquals(4,p.getCost());


    }

}

class Plus2InfluenceTest{
    @Test
    public void testDoEffect(){
        Game game = new Game(2, true);
        game.addPlayer("p1");
        game.addPlayer("p2");

        Plus2Influence p= new Plus2Influence(game);

        game.getPlayers().get(game.getCurrentPlayer()).setCoins(3);
        game.getIslands().get(0).addStudents(Color.GREEN,2);
        game.getCurrentPlayerInstance().getSchoolDashboard().addStudentToDiningRoom(Color.GREEN);

        assertEquals(2,p.getCost());
        assertEquals(false,game.isPlus2Influence());
        p.doEffect();
        assertEquals(true,game.isPlus2Influence());
        assertEquals(3,p.getCost());

        assertEquals(4,game.countInfluence(game.getCurrentPlayerInstance(),game.getIslands().get(0)));
        assertEquals(false,game.isPlus2Influence());

    }
}

class BlockColorOnceTest{
    @Test
    public void testDoEffect(){
        Game game = new Game(2, true);
        game.addPlayer("p1");
        game.addPlayer("p2");
        Color choosen=Color.YELLOW;
        BlockColorOnce p= new BlockColorOnce(game);

        //clear the island
        cleanIsland(game.getIslands().get(0));

        game.getPlayers().get(game.getCurrentPlayer()).setCoins(3);

        //initialize school dashboard
        game.setCurrentPlayer(0);
        game.getIslands().get(0).addStudents(choosen,2);
        game.getPlayers().get(0).getSchoolDashboard().addStudentToDiningRoom(choosen);
        assertEquals(true,game.getPlayers().get(0).getSchoolDashboard().hasProfessor(choosen));

        assertEquals(3,p.getCost());
        assertEquals(false,game.isBlockColorOnce());
        assertEquals(null,game.getBlockedColor());

        p.doEffect(choosen);

        assertEquals(choosen,game.getBlockedColor());

        assertEquals(null,game.playerWithHigherInfluence(game.getIslands().get(0)));
        assertEquals(false,game.isBlockColorOnce());
        assertEquals(null,game.getBlockedColor());


        assertEquals(4,p.getCost());

    }
    private void cleanIsland(IslandGroup c){
        for(Color p : Color.values()){
            while(c.getStudents().get(p)>0){
                c.getStudents().put(p,0);
            }
        }
    }
}
class Exchange2StudentsTest{
    @Test
    public void testDoEffect() throws MissingStudentException {
        Game game = new Game(2, true);
        game.addPlayer("p1");
        game.addPlayer("p2");

        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().clear();
        Exchange2Students p=new Exchange2Students(game);

        game.getPlayers().get(game.getCurrentPlayer()).setCoins(3);

        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().addStudentToEntrance(Color.YELLOW);
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().addStudentToEntrance(Color.YELLOW);
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().addStudentToEntrance(Color.BLUE);

        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().addStudentToDiningRoom(Color.RED);
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().addStudentToDiningRoom(Color.GREEN);

        //controllo numero di studenti in entrance
        int sumDiningRoom1=0;
        for(Color c: game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getDiningRoom().keySet()){
            sumDiningRoom1 = sumDiningRoom1 +game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getDiningRoom().get(c);
        }
        //controllo numero di studenti in diningRoom
        int sumEntrance1=0;
        for(Color c: game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().keySet()){
            sumEntrance1 = sumEntrance1 +game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().get(c);
        }

        //inizializzo enum map ChoosenFromEntrance

        EnumMap<Color,Integer> choosenFromEntrance = new EnumMap<>(Color.class);
        choosenFromEntrance.put(Color.YELLOW,1);
        choosenFromEntrance.put(Color.BLUE,1);

        EnumMap<Color,Integer> choosenFromDiningRoom = new EnumMap<>(Color.class);
        choosenFromDiningRoom.put(Color.GREEN,1);
        choosenFromDiningRoom.put(Color.RED,1);

        //eseguo effetto
        p.doEffect(choosenFromEntrance,choosenFromDiningRoom);

        //controllo numero di studenti in entrance
        int sumDiningRoom2=0;
        for(Color c: game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getDiningRoom().keySet()){
            sumDiningRoom2 = sumDiningRoom2 +game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getDiningRoom().get(c);
        }
        //controllo numero di studenti in diningRoom
        int sumEntrance2=0;
        for(Color c: game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().keySet()){
            sumEntrance2 = sumEntrance2 +game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().get(c);
        }

        assertEquals(sumDiningRoom1,sumDiningRoom2);
        assertEquals(sumEntrance1,sumEntrance2);
        assertEquals(0,game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().get(Color.BLUE));
        assertEquals(1,game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().get(Color.YELLOW));

        assertEquals(1,game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getDiningRoom().get(Color.YELLOW));
        assertEquals(1,game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getDiningRoom().get(Color.BLUE));
    }

}


class Choose1DiningRoomTest{
    @Test
    public void testDoEffect(){
        Game game= new Game(2, true);
        game.addPlayer("p1");
        game.addPlayer("p2");

        Choose1DiningRoom p=new Choose1DiningRoom(game);

        game.getPlayers().get(game.getCurrentPlayer()).setCoins(3);
        //game.getPlayers().get(0).getSchoolDashboard().setCurrentGame(game);
        //game.getPlayers().get(1).getSchoolDashboard().setCurrentGame(game);

        p.getStudents().put(Color.YELLOW,2);
        p.getStudents().put(Color.RED,2);

        Color choosen= Color.YELLOW;
        //verifico che il colore non sia presente nella sala
        assertEquals(0,game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getDiningRoom().get(choosen));


        p.doEffect(choosen);
        assertEquals(1,game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getDiningRoom().get(choosen));


    }

    @Test
    public void testInit(){
        Game game= new Game(2, true);
        Choose1DiningRoom p=new Choose1DiningRoom(game);

        p.init();
        int sum=0;
        for(Color c: p.getStudents().keySet()){
            sum=sum + p.getStudents().get(c);
        }
        assertEquals(4,sum);

    }

}

class AllRemoveColorTest{
    @Test
    public void testDoEffect(){
        Game game=new Game(3, true);
        game.addPlayer("p1");
        game.addPlayer("p2");
        game.addPlayer("p3");

        AllRemoveColor p= new AllRemoveColor(game);
        game.getPlayers().get(game.getCurrentPlayer()).setCoins(3);


        for(int i=0;i<4;i++)
            game.getPlayers().get(0).getSchoolDashboard().addStudentToDiningRoom(Color.RED);
        for(int i=0;i<2;i++)
            game.getPlayers().get(1).getSchoolDashboard().addStudentToDiningRoom(Color.RED);
        game.getPlayers().get(2).getSchoolDashboard().addStudentToDiningRoom(Color.GREEN);

        Color chosen=Color.RED;

        assertEquals(4,game.getPlayers().get(0).getSchoolDashboard().getDiningRoom().get(chosen));
        assertEquals(2,game.getPlayers().get(1).getSchoolDashboard().getDiningRoom().get(chosen));

        p.doEffect(chosen);

        assertEquals(1,game.getPlayers().get(0).getSchoolDashboard().getDiningRoom().get(chosen));
        assertEquals(0,game.getPlayers().get(1).getSchoolDashboard().getDiningRoom().get(chosen));

        assertEquals(1,game.getPlayers().get(2).getSchoolDashboard().getDiningRoom().get(Color.GREEN));

    }

}

class Choose3toEntranceTest{
    @Test
    public void testDoEffect() throws MissingStudentException {
        Game game = new Game(2, true);
        game.addPlayer("p1");
        game.addPlayer("p2");
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().clear();
        Choose3toEntrance p = new Choose3toEntrance(game);
        game.getPlayers().get(game.getCurrentPlayer()).setCoins(3);

        p.clear();

        //manual card init
        p.getStudents().put(Color.BLUE, 1);
        p.getStudents().put(Color.RED, 2);
        p.getStudents().put(Color.YELLOW, 1);
        p.getStudents().put(Color.GREEN, 2);

        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().addStudentToEntrance(Color.BLUE);
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().addStudentToEntrance(Color.PINK);
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().addStudentToEntrance(Color.PINK);

        //manual input init
        EnumMap<Color, Integer> fromCard = new EnumMap<>(Color.class);
        fromCard.put(Color.GREEN, 1);
        fromCard.put(Color.YELLOW, 1);
        fromCard.put(Color.RED, 1);
        EnumMap<Color, Integer> fromEntrance = new EnumMap<>(Color.class);
        fromEntrance.put(Color.BLUE, 1);
        fromEntrance.put(Color.PINK, 2);

        //init verification
        assertEquals(6, p.totalNumberOfStudents(p.getStudents()));

        p.doEffect(fromCard, fromEntrance);

        //card verification after the effect
        assertEquals(6, p.totalNumberOfStudents(p.getStudents()));
            assertEquals(2, p.getStudents().get(Color.BLUE));
            assertEquals(1, p.getStudents().get(Color.GREEN));
            assertEquals(1, p.getStudents().get(Color.RED));
            assertEquals(2, p.getStudents().get(Color.PINK));

        //entrance verification after the effect
        assertEquals(1, game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().get(Color.RED));
        assertEquals(1, game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().get(Color.YELLOW));
        assertEquals(1, game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().get(Color.GREEN));

    }
    @Test
    public void testShouldThrowRuntimeExceptionWhenDifferentStudentNumber(){
        Game game = new Game(2, true);
        game.addPlayer("p1");
        game.addPlayer("p2");
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().clear();
        Choose3toEntrance p = new Choose3toEntrance(game);

        EnumMap<Color, Integer> fromCard = new EnumMap<>(Color.class);
        fromCard.put(Color.GREEN, 1);
        fromCard.put(Color.YELLOW, 1);
        fromCard.put(Color.RED, 1);
        EnumMap<Color, Integer> fromEntrance = new EnumMap<>(Color.class);
        fromEntrance.put(Color.BLUE, 1);
        fromEntrance.put(Color.PINK, 1);

        EnumMap<Color, Integer> support1 = new EnumMap<>(Color.class);
        EnumMap<Color, Integer> support2 = new EnumMap<>(Color.class);
        support1 = fromCard.clone();
        support2 = fromEntrance.clone();

        Assertions.assertThrows(IllegalArgumentException.class , () -> p.doEffect(fromCard, fromEntrance));



    }
    @Test
    public void testInit(){
        Game game = new Game(2, true);
        game.addPlayer("p1");
        game.addPlayer("p2");
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().clear();
        Choose3toEntrance p = new Choose3toEntrance(game);

        p.init();
        int sum=0;
        for(Color c: p.getStudents().keySet()){
            sum=sum+p.getStudents().get(c);
        }
        assertEquals(6,sum);
    }
}
class ChooseIslandTest{
    @Test
    public void testDoEffectOccupiedBy(){
        Game game = new Game(2, true);
        ChooseIsland p = new ChooseIsland(game);

        game.addPlayer("player1");
        game.addPlayer("player2");

        //for(Player x : game.getPlayers()){
        //    x.getSchoolDashboard().setCurrentGame(game);
         //   game.getPlayers().get(0).setCoins(3);
        //}
        game.setCurrentPlayer(0);

        //isola occupata 2 dal giocatore 1 decremento torri
        game.getIslands().get(2).setOccupiedBy(game.getPlayers().get(1));
        game.getPlayers().get(1).getSchoolDashboard().removeTowers(1);
        //aggiungo 1 studente rosso e 1 verde all'isola
        game.getIslands().get(2).addStudents(Color.RED,1);
        game.getIslands().get(2).addStudents(Color.GREEN,1);
        game.getIslands().get(2).addStudents(Color.YELLOW,1);
        //metto 1 studenti nella dining room di entrambi gli studenti
        game.getPlayers().get(1).getSchoolDashboard().addStudentToDiningRoom(Color.RED);
        game.getPlayers().get(0).getSchoolDashboard().addStudentToDiningRoom(Color.YELLOW);
        game.getPlayers().get(0).getSchoolDashboard().addStudentToDiningRoom(Color.GREEN);

        assertEquals(7,game.getPlayers().get(1).getSchoolDashboard().getTowers());
        assertEquals(8,game.getPlayers().get(0).getSchoolDashboard().getTowers());
        //ora il giocatore 1 ha il prof
        p.doEffect(2);

        assertEquals(game.getPlayers().get(0),game.getIslands().get(2).getOccupiedBy());
        assertEquals(7,game.getPlayers().get(0).getSchoolDashboard().getTowers());
        assertEquals(8,game.getPlayers().get(1).getSchoolDashboard().getTowers());
    }
    @Test
    public void testDoEffect(){
        Game game = new Game(2, true);
        game.addPlayer("p1");
        game.addPlayer("p2");

        ChooseIsland p= new ChooseIsland(game);

        //for(Player x : game.getPlayers()){

            //x.getSchoolDashboard().setCurrentGame(game);

        //}
        game.getPlayers().get(0).setCoins(3);
        game.setCurrentPlayer(0);

        game.getIslands().get(2).addStudents(Color.GREEN);
        game.getPlayers().get(0).getSchoolDashboard().addStudentToDiningRoom(Color.GREEN);

        p.doEffect(2);

        assertEquals(7,game.getPlayers().get(0).getSchoolDashboard().getTowers());
        assertEquals(game.getPlayers().get(0),game.getIslands().get(2).getOccupiedBy());

    }


}

class TempControlProfTest{
    @Test
    public void testDoEffect(){
        Game game = new Game(2, true);
        game.addPlayer("p1");
        game.addPlayer("p2");

        TempControlProf p= new TempControlProf(game);

        //for(Player g: game.getPlayers()) {
        //    g.setCoins(3);
        //    g.getSchoolDashboard().setCurrentGame(game);
        //}
        game.setCurrentPlayer(0);
        game.getPlayers().get(0).getSchoolDashboard().addStudentToDiningRoom(Color.RED);
        game.getPlayers().get(0).getSchoolDashboard().addStudentToDiningRoom(Color.RED);
        assertTrue(game.getPlayers().get(0).getSchoolDashboard().getProfessors().contains(Color.RED));

        game.getPlayers().get(1).getSchoolDashboard().addStudentToDiningRoom(Color.RED);
        game.getPlayers().get(1).getSchoolDashboard().addStudentToDiningRoom(Color.RED);
        assertFalse(game.getPlayers().get(1).getSchoolDashboard().getProfessors().contains(Color.RED));

        game.setCurrentPlayer(1);
        p.doEffect();

        assertFalse(game.getPlayers().get(0).getSchoolDashboard().getProfessors().contains(Color.RED));
        assertTrue(game.getPlayers().get(1).getSchoolDashboard().getProfessors().contains(Color.RED));


    }
    @Test
    public void testResetTempControlProf(){
        Game game = new Game(2, true);
        game.addPlayer("p1");
        game.addPlayer("p2");

        TempControlProf p = new TempControlProf(game);

        for(Player g: game.getPlayers()) {
            g.setCoins(3);
            //g.getSchoolDashboard().setCurrentGame(game);
        }
        game.setCurrentPlayer(0);
        game.getPlayers().get(0).getSchoolDashboard().addStudentToDiningRoom(Color.RED);
        game.getPlayers().get(0).getSchoolDashboard().addStudentToDiningRoom(Color.RED);
        assertTrue(game.getPlayers().get(0).getSchoolDashboard().getProfessors().contains(Color.RED));

        game.getPlayers().get(1).getSchoolDashboard().addStudentToDiningRoom(Color.RED);
        game.getPlayers().get(1).getSchoolDashboard().addStudentToDiningRoom(Color.RED);
        assertFalse(game.getPlayers().get(1).getSchoolDashboard().getProfessors().contains(Color.RED));

        game.setCurrentPlayer(1);
        p.doEffect();

        assertFalse(game.getPlayers().get(0).getSchoolDashboard().getProfessors().contains(Color.RED));
        assertTrue(game.getPlayers().get(1).getSchoolDashboard().getProfessors().contains(Color.RED));

        p.resetTempControlProf();

        assertTrue(game.getPlayers().get(0).getSchoolDashboard().getProfessors().contains(Color.RED));
        assertFalse(game.getPlayers().get(1).getSchoolDashboard().getProfessors().contains(Color.RED));
        assertTrue(p.getPlayerModified().isEmpty());
    }


}

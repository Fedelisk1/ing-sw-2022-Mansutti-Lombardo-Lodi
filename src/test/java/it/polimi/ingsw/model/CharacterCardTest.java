package it.polimi.ingsw.model;



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

        Choose1ToIsland p = new Choose1ToIsland();
        p.setCurrentGame(game);
        p.init();

        game.getPlayers().get(game.getCurrentPlayer()).setCoins(2);

        //controlliamo se sono 4 gli studenti di extracted
        int sum=0;
        for(Color c : p.getExtracted().keySet()){
            sum= sum+ p.getExtracted().get(c);
        }
        assertEquals(4,sum);
        //stampa i 4 studenti estratti sulla carta
        System.out.println(p.getExtracted());

        // se ne sceglie 1
        Color choosen;
        choosen= Color.GREEN;

        //si sceglie l'isola in cui metterlo
        int isl=1;
        //metto in x il numero di studenti del colore choosen prima del do effect
        int x= game.getIslands().get(isl).getStudents().get(choosen);

        if(p.getExtracted().containsKey(choosen)) {
            p.doEffect(choosen, isl);
            //vediamo se è stato veramente aggiunto lo studente all'isola
            assertEquals(1 + x, game.getIslands().get(isl).getStudents().get(choosen));
            sum=0;
            for(Color c : p.getExtracted().keySet()){
                sum= sum+ p.getExtracted().get(c);
            }
            assertEquals(4,sum);
        } else {
            Assertions.assertThrows(IllegalArgumentException.class , () -> {p.doEffect(choosen,isl);});
            System.out.println("AssertThrows");
        }
    }

}




class TwoAdditionalMovesTest {
    @Test
    public void testDoEffect() {
        Game game = new Game(2, true);
        TwoAdditionalMoves p= new TwoAdditionalMoves();
        p.setCurrentGame(game);
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
        NoEntryIsland p=new NoEntryIsland();
        p.setCurrentGame(game);
        game.getPlayers().get(game.getCurrentPlayer()).setCoins(3);
        int islNumb=1;

        assertEquals(2,p.cost);

        assertEquals(false,game.getIslands().get(islNumb).isNoEntryIsland());
        p.doEffect(islNumb);
        assertEquals(true,game.getIslands().get(islNumb).isNoEntryIsland());

        assertEquals(3,p.cost);

    }


}

class BlockTowerTest{
    @Test
    public void testDoEffect(){
        Game game = new Game(2, true);
        BlockTower p=new BlockTower();
        p.setCurrentGame(game);
        game.getPlayers().get(game.getCurrentPlayer()).setCoins(3);

        assertEquals(3,p.cost);

        assertEquals(false,game.getIslands().get(game.getMotherNaturePosition()).isBlockTower_CC());

        p.doEffect();

        assertEquals(true,game.getIslands().get(game.getMotherNaturePosition()).isBlockTower_CC());

        assertEquals(4,p.cost);

    }

}

class Plus2InfluenceTest{
    @Test
    public void testDoEffect(){
        Game game = new Game(2, true);
        Plus2Influence p= new Plus2Influence();
        p.setCurrentGame(game);
        game.getPlayers().get(game.getCurrentPlayer()).setCoins(3);

        assertEquals(2,p.cost);
        assertEquals(false,game.getIslands().get(game.getMotherNaturePosition()).isPlus2Influence_CC());
        p.doEffect();
        assertEquals(true,game.getIslands().get(game.getMotherNaturePosition()).isPlus2Influence_CC());
        assertEquals(3,p.cost);

    }
}

class BlockColorOnceTest{
    @Test
    public void testDoEffect(){
        Game game = new Game(2, true);
        BlockColorOnce p= new BlockColorOnce();
        p.setCurrentGame(game);
        game.getPlayers().get(game.getCurrentPlayer()).setCoins(3);
        Color choosen=Color.YELLOW;

        assertEquals(3,p.cost);
        assertEquals(false,game.getIslands().get(game.getMotherNaturePosition()).isBlockColorOnce_CC());
        assertEquals(null,game.getIslands().get(game.getMotherNaturePosition()).getBlockedColor());

        p.doEffect(choosen);

        assertEquals(true,game.getIslands().get(game.getMotherNaturePosition()).isBlockColorOnce_CC());
        assertEquals(choosen,game.getIslands().get(game.getMotherNaturePosition()).getBlockedColor());

        assertEquals(4,p.cost);

    }
}
class Exchange2StudentsTest{
    @Test
    public void testDoEffect(){
        Game game = new Game(2, true);
        Exchange2Students p=new Exchange2Students();
        p.setCurrentGame(game);
        game.getPlayers().get(game.getCurrentPlayer()).setCoins(3);
        game.getPlayers().get(0).setCurrentGame(game);
        game.getPlayers().get(0).getSchoolDashboard().setCurrentGame(game);

        game.getPlayers().get(0).getSchoolDashboard().addStudentToEntrance(Color.YELLOW);
        game.getPlayers().get(0).getSchoolDashboard().addStudentToEntrance(Color.YELLOW);
        game.getPlayers().get(0).getSchoolDashboard().addStudentToEntrance(Color.BLUE);

        game.getPlayers().get(0).getSchoolDashboard().addStudentToDiningRoom(Color.RED);
        game.getPlayers().get(0).getSchoolDashboard().addStudentToDiningRoom(Color.GREEN);

        //controllo numero di studenti in entrance
        int sumDiningRoom1=0;
        for(Color c: game.getPlayers().get(0).getSchoolDashboard().getDiningRoom().keySet()){
            sumDiningRoom1 = sumDiningRoom1 +game.getPlayers().get(0).getSchoolDashboard().getDiningRoom().get(c);
        }
        //controllo numero di studenti in diningRoom
        int sumEntrance1=0;
        for(Color c: game.getPlayers().get(0).getSchoolDashboard().getEntrance().keySet()){
            sumEntrance1 = sumEntrance1 +game.getPlayers().get(0).getSchoolDashboard().getEntrance().get(c);
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
        for(Color c: game.getPlayers().get(0).getSchoolDashboard().getDiningRoom().keySet()){
            sumDiningRoom2 = sumDiningRoom2 +game.getPlayers().get(0).getSchoolDashboard().getDiningRoom().get(c);
        }
        //controllo numero di studenti in diningRoom
        int sumEntrance2=0;
        for(Color c: game.getPlayers().get(0).getSchoolDashboard().getEntrance().keySet()){
            sumEntrance2 = sumEntrance2 +game.getPlayers().get(0).getSchoolDashboard().getEntrance().get(c);
        }

        assertEquals(sumDiningRoom1,sumDiningRoom2);
        assertEquals(sumEntrance1,sumEntrance2);
        assertEquals(0,game.getPlayers().get(0).getSchoolDashboard().getEntrance().get(Color.BLUE));
        assertEquals(1,game.getPlayers().get(0).getSchoolDashboard().getEntrance().get(Color.YELLOW));

        assertEquals(1,game.getPlayers().get(0).getSchoolDashboard().getDiningRoom().get(Color.YELLOW));
        assertEquals(1,game.getPlayers().get(0).getSchoolDashboard().getDiningRoom().get(Color.BLUE));
    }

}

class Choose1DiningRoomTest{
    @Test
    public void testDoEffect(){
        Game game= new Game(2, true);
        Choose1DiningRoom p=new Choose1DiningRoom();
        p.setCurrentGame(game);
        game.getPlayers().get(game.getCurrentPlayer()).setCoins(3);
        game.getPlayers().get(0).setCurrentGame(game);
        game.getPlayers().get(0).getSchoolDashboard().setCurrentGame(game);
        game.getPlayers().get(1).setCurrentGame(game);
        game.getPlayers().get(1).getSchoolDashboard().setCurrentGame(game);
        p.init();

        Color choosen= Color.YELLOW;
        //verifico che il colore non sia presente nella sala
        assertEquals(0,game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getDiningRoom().get(choosen));

        if(p.getExtracted().containsKey(choosen)){
            p.doEffect(choosen);
            assertEquals(1,game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getDiningRoom().get(choosen));

        }else {
            System.out.println("Entra nella Throw");
            Assertions.assertThrows(IllegalArgumentException.class , () -> {p.doEffect(choosen);});
        }


    }

}

class AllRemoveColorTest{
    @Test
    public void testDoEffect(){
        Game game=new Game(3, true);
        AllRemoveColor p= new AllRemoveColor();
        p.setCurrentGame(game);
        game.getPlayers().get(game.getCurrentPlayer()).setCoins(3);

        for(Player g: game.getPlayers()){
            g.setCurrentGame(game);
            g.getSchoolDashboard().setCurrentGame(game);
        }
        for(int i=0;i<4;i++)
            game.getPlayers().get(0).getSchoolDashboard().addStudentToDiningRoom(Color.RED);
        for(int i=0;i<2;i++)
            game.getPlayers().get(1).getSchoolDashboard().addStudentToDiningRoom(Color.RED);
        game.getPlayers().get(2).getSchoolDashboard().addStudentToDiningRoom(Color.GREEN);


        Color choosen=Color.RED;

        assertEquals(4,game.getPlayers().get(0).getSchoolDashboard().getDiningRoom().get(choosen));
        assertEquals(2,game.getPlayers().get(1).getSchoolDashboard().getDiningRoom().get(choosen));

        p.doEffect(choosen);

        assertEquals(1,game.getPlayers().get(0).getSchoolDashboard().getDiningRoom().get(choosen));
        assertEquals(0,game.getPlayers().get(1).getSchoolDashboard().getDiningRoom().get(choosen));

        assertEquals(1,game.getPlayers().get(2).getSchoolDashboard().getDiningRoom().get(Color.GREEN));

    }

}

class Choose3toEntranceTest{
    @Test
    public void testDoEffect() {
        Game game = new Game(2, true);
        Choose3toEntrance p = new Choose3toEntrance();
        p.setCurrentGame(game);
        game.getPlayers().get(game.getCurrentPlayer()).setCoins(3);
        game.getPlayers().get(game.getCurrentPlayer()).setCurrentGame(game);
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().setCurrentGame(game);

        p.getExtracted().put(Color.BLUE, 1);
        p.getExtracted().put(Color.RED, 2);
        p.getExtracted().put(Color.YELLOW, 1);
        p.getExtracted().put(Color.GREEN, 2);


        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().addStudentToEntrance(Color.BLUE);
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().addStudentToEntrance(Color.PINK);
        game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().addStudentToEntrance(Color.PINK);


        EnumMap<Color, Integer> fromCard = new EnumMap<>(Color.class);
        fromCard.put(Color.GREEN, 1);
        fromCard.put(Color.YELLOW, 1);
        fromCard.put(Color.RED, 1);
        EnumMap<Color, Integer> fromEntrance = new EnumMap<>(Color.class);
        fromEntrance.put(Color.BLUE, 1);
        fromEntrance.put(Color.PINK, 2);

        EnumMap<Color, Integer> support1 = new EnumMap<>(Color.class);
        EnumMap<Color, Integer> support2 = new EnumMap<>(Color.class);
        support1 = fromCard.clone();
        support2 = fromEntrance.clone();

        if (p.totalNumberofStudent(support1) == p.totalNumberofStudent(support2)) {

            int sum1 = 0;
            for (Color c : p.getExtracted().keySet())
                sum1 = sum1 + p.getExtracted().get(c);

            assertEquals(6, sum1);

            p.doEffect(fromCard, fromEntrance);

            int sum2 = 0;

            for (Color c : p.getExtracted().keySet())
                sum2 = sum2 + p.getExtracted().get(c);

            //verifica della carta
            assertEquals(6, sum2);
            assertEquals(2, p.getExtracted().get(Color.BLUE));
            assertEquals(1, p.getExtracted().get(Color.GREEN));
            assertEquals(1, p.getExtracted().get(Color.RED));
            assertEquals(2, p.getExtracted().get(Color.PINK));

            //verifica dell'ingresso
            assertEquals(1, game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().get(Color.RED));
            assertEquals(1, game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().get(Color.YELLOW));
            assertEquals(1, game.getPlayers().get(game.getCurrentPlayer()).getSchoolDashboard().getEntrance().get(Color.GREEN));
        } else {
            System.out.println("Entra nella Throw");
            Assertions.assertThrows(IllegalArgumentException.class , () -> {p.doEffect(fromCard,fromEntrance);});
        }
    }
}
class TempControlProfTest{
    @Test
    public void testDoEffect(){
        Game game = new Game(2, true);
        TempControlProf p= new TempControlProf();
        p.setCurrentGame(game);
        for(Player g: game.getPlayers()) {
            g.setCurrentGame(game);
            g.getSchoolDashboard().setCurrentGame(game);
        }
        //DA TERMINARE

    }



}


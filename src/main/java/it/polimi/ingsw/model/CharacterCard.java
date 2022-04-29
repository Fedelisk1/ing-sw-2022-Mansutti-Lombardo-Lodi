package it.polimi.ingsw.model;

import java.util.EnumMap;

public abstract class CharacterCard {
    public int cost;
    public boolean usageCount;
    public Game currentGame;



    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }
}

class Choose1ToIsland extends CharacterCard{

    private EnumMap<Color,Integer> extracted;
    private EnumMap<Color,Integer> extractedFromBag;

    private int cost;



    public Choose1ToIsland() {
        extracted=new EnumMap<>(Color.class);
        extractedFromBag= new EnumMap<>(Color.class);
        cost=1;
        extracted=Game.extractFromBag(4);
    }



    public void doEffect(Color c,int islandNumber) {

        Game.getCurrentPlayer().chooseStudent1(extracted,c);

         currentGame.getIslands().get(islandNumber).addStudents(c);

         extractedFromBag =  currentGame.extractFromBag(1);

         for(Color x: extractedFromBag.keySet()){
               if (!extracted.containsKey(x))
                   extracted.put(x, 1);
               else extracted.put(x, extracted.get(x) + 1);

              extractedFromBag.remove(x);
        }



    }

}

class TempControlProf extends CharacterCard{
    private int cost;

    public TempControlProf() {
        cost=2;

    }
}

class ChooseIsland extends CharacterCard{
    public ChooseIsland() {
        cost=3;
    }
}

class BlockTower extends CharacterCard{
    public BlockTower() {
        cost=3;
    }
}

class NoEntryIsland extends CharacterCard{
    public NoEntryIsland() {
        cost=2;
    }
}
class TwoAdditionalMoves extends CharacterCard{
    public TwoAdditionalMoves() {
        cost=1;
    }
    public void doEffect() {
        currentGame.getCurrentPlayer().setMaxPosition(currentGame.getCurrentPlayer().getMaxPosition()+2);
    }
}

class Choose3toEntrance extends CharacterCard{
    private EnumMap<Color,Integer> extracted;
    private int cost;

    public Choose3toEntrance() {
        //DA TERMINARE
        extracted=new EnumMap<Color, Integer>(Color.class);
        extracted = currentGame.extractFromBag(6);
        cost=1;
    }


    public void doEffect() {

    }
}

class Plus2Influence extends CharacterCard{
    public Plus2Influence() {
        cost=2;
    }


}

class BlockColorOnce extends CharacterCard{
    public BlockColorOnce() {
        cost=3;
    }
}

class Exchange2Students extends CharacterCard{
    public Exchange2Students() {
        cost=1;
    }
}

class Choose1ToEntrance extends CharacterCard{
    public Choose1ToEntrance() {
        cost=1;
    }
}

class AllRemoveColor extends CharacterCard{
    public AllRemoveColor() {
        cost=3;
    }
}





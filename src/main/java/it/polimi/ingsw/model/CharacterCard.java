package it.polimi.ingsw.model;

import java.util.EnumMap;

public abstract class CharacterCard {
    public int cost;
    public boolean used;
    public Game currentGame;


    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

}

class Choose1ToIsland extends CharacterCard{

    private EnumMap<Color,Integer> extracted;
    private EnumMap<Color,Integer> extractedFromBag;
    private boolean used;
    private int cost;



    public Choose1ToIsland() {
        
        extracted=new EnumMap<>(Color.class);
        extractedFromBag= new EnumMap<>(Color.class);
        cost=1;
        extracted = currentGame.extractFromBag(4);
    }

    /**
     * From 4 students on the card, 1 is chosen and placed on the island
     * @param c is a color
     * @param islandNumber the number of the island where the student will be placed
     */

    public void doEffect(Color c,int islandNumber) {

         cost=2;
         extracted.put(c, extracted.get(c)-1);

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

    public void doEffect(){
        cost=3;
    }
}

class ChooseIsland extends CharacterCard{
    public ChooseIsland() {
        cost=3;
    }
    public void doEffect(){
        cost=4;
    }
}

class BlockTower extends CharacterCard{
    public BlockTower() {
        cost=3;
    }

    /**
     * Set true the boolean blockTower of IslandGroup
     */
    public void doEffect(){
        cost=4;
        currentGame.getIslands().get(currentGame.getMotherNaturePosition()).setBlockTower_CC(true);
    }
}

class NoEntryIsland extends CharacterCard{
    int availableUses=4;
    public NoEntryIsland() {
        availableUses=4;
        cost=2;
    }
    /**
     * Set true the boolean noEntryIsland of IslandGroup and decrement availableUses
     */
    public void doEffect(){
        cost=3;

        if(availableUses>0){

            availableUses=availableUses-1;
            currentGame.getIslands().get(currentGame.getMotherNaturePosition()).setNoEntryIsland(true);

        }else  throw new IllegalArgumentException("Maximum number of effect uses");

    }

}
class TwoAdditionalMoves extends CharacterCard{
    public TwoAdditionalMoves() {
        cost=1;
    }

    /**
     * Add 2 to the attribute maxPosition of player
     */

    public void doEffect() {
        cost=2;
        currentGame.getCurrentPlayer().setMaxPosition(currentGame.getCurrentPlayer().getMaxPosition()+2);
    }
}

class Choose3toEntrance extends CharacterCard {
    private EnumMap<Color, Integer> extracted;
    private int cost;

    public Choose3toEntrance() {

        extracted = new EnumMap<Color, Integer>(Color.class);
        extracted = currentGame.extractFromBag(6);
        cost = 1;
    }

    /**
     * Check that the number of selected students is the same and then move the students from the card to the entrance
     * @param chosenFromCard represent the students chosen from the card
     * @param chosenFromEntrance represent the students chosen from the entrance
     *
     */

    public void doEffect(EnumMap<Color, Integer> chosenFromCard, EnumMap<Color, Integer> chosenFromEntrance) {
        cost=2;
        if(totalNumberofStudent(chosenFromEntrance)!=totalNumberofStudent(chosenFromCard))throw new IllegalArgumentException("different number of selected students");

        for (Color c : chosenFromCard.keySet()) {
            while (chosenFromCard.get(c) > 0){

                chosenFromCard.put(c, chosenFromCard.get(c) - 1);
                extracted.put(c, extracted.get(c) - 1);
                currentGame.getCurrentPlayer().getSchoolDashboard().addStudentToEntrance(c);

            }


        }
        for(Color c : chosenFromEntrance.keySet()){
            while(chosenFromEntrance.get(c)>0){
                chosenFromEntrance.put(c, chosenFromEntrance.get(c)-1);
                if(extracted.get(c)==null)
                    extracted.putIfAbsent(c,1);
                else
                extracted.put(c, extracted.get(c)+1);
                currentGame.getCurrentPlayer().getSchoolDashboard().removeStudentFromEntrance(c);
            }


        }
    }

    /**
     * Calculate the total number of students
     * @param e is the EnumMap
     * @return total number of students
     */
    public int totalNumberofStudent(EnumMap<Color,Integer> e){
        int sum=0;

        for(Color c : e.keySet()){
            while(e.get(c)>0){
                e.put(c,e.get(c)-1);
                sum++;
            }
        }
        return sum;
    }

}

class Plus2Influence extends CharacterCard{
    public Plus2Influence() {
        cost=2;
    }

    /**
     * Set true the boolean plus2Influence of IslandGroup
     */
    public void doEffect(){
        cost=3;
        currentGame.getIslands().get(currentGame.getMotherNaturePosition()).setPlus2Influence_CC(true);
    }


}

class BlockColorOnce extends CharacterCard{
    public BlockColorOnce() {
        cost=3;
    }

    /**
     *  Set true the boolean blockColorOnce of IslandGroup and set the color to block
     * @param c is the color to block
     */
    public void doEffect(Color c){
        cost=4;
        currentGame.getIslands().get(currentGame.getMotherNaturePosition()).setBlockedColor(c);
        currentGame.getIslands().get(currentGame.getMotherNaturePosition()).setBlockColorOnce_CC(true);
    }
}

class Exchange2Students extends CharacterCard{
    public Exchange2Students() {
        cost=1;
    }

    /**
     * This method allows you to exchange two students from the entrance to the dining room
     * @param chosenFromEntrance are the cards chosen from the entrance
     * @param chosenFromDiningRoom are the cards chosen from the dining room
     */
    public void doEffect(EnumMap<Color,Integer> chosenFromEntrance,EnumMap<Color,Integer> chosenFromDiningRoom){

        cost=2;

        for(Color c : chosenFromEntrance.keySet()){
            while(chosenFromEntrance.get(c)>0){

                chosenFromEntrance.put(c,chosenFromEntrance.get(c)-1);
                currentGame.getCurrentPlayer().getSchoolDashboard().addStudentToDiningRoom(c);
                currentGame.getCurrentPlayer().getSchoolDashboard().removeStudentFromEntrance(c);


            }
        }

        for(Color c : chosenFromDiningRoom.keySet()){
            while(chosenFromDiningRoom.get(c)>0){
                chosenFromDiningRoom.put(c,chosenFromDiningRoom.get(c)-1);
                currentGame.getCurrentPlayer().getSchoolDashboard().addStudentToEntrance(c);
                currentGame.getCurrentPlayer().getSchoolDashboard().removeStudentFromDiningRoom(c);
            }
        }

    }


}

class Choose1DiningRoom extends CharacterCard{
    private EnumMap<Color,Integer> extracted;
    private EnumMap<Color,Integer> extractedFromBag;

    public Choose1DiningRoom() {
        cost=2;
        extracted=new EnumMap<>(Color.class);
        extractedFromBag=new EnumMap<>(Color.class);
        extracted= currentGame.extractFromBag(4);

    }

    /**
     * From the 4 students on the card, one is chosen and placed in the dining room
     * @param c color of the student
     */
    public void doEffect(Color c){
        cost=3;
        currentGame.getCurrentPlayer().getSchoolDashboard().addStudentToDiningRoom(c);
        extracted.put(c, extracted.get(c)-1);

        extractedFromBag =  currentGame.extractFromBag(1);

        for(Color x: extractedFromBag.keySet()) {
            if (!extracted.containsKey(x))
                extracted.put(x, 1);
            else extracted.put(x, extracted.get(x) + 1);

            extractedFromBag.remove(x);
        }

    }
}

class AllRemoveColor extends CharacterCard{
    public AllRemoveColor() {
        cost=3;
    }

    /**
     * For each player three students of color c are removed from the dining room, but if you have fewer pieces
     * you have to remove the ones you have.
     * @param c is the color
     */
    public void doEffect(Color c){
        cost=4;

        for(Player p: currentGame.getPlayers()){
            int numberOfStudents=p.getSchoolDashboard().getDiningRoom().get(c);

            if(numberOfStudents>2){
                for(int i=0;i<3;i++)
                    p.getSchoolDashboard().removeStudentFromDiningRoom(c);
            }else if(numberOfStudents==2){
                for(int i=0;i<2;i++)
                    p.getSchoolDashboard().removeStudentFromDiningRoom(c);
            }else if(numberOfStudents==1)
                p.getSchoolDashboard().removeStudentFromDiningRoom(c);

        }

    }


}





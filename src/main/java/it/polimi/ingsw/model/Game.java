package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Random;

public class Game {
    private EnumMap<Color, Integer> bag;
    private int motherNaturePosition = 0;
    private final int MAX_ISLANDS = 12;
    private ArrayList<IslandGroup> islands;
    private ArrayList<Player> players;
    private int currentPlayer;
    private ArrayList<CharacterCard> characterCards;
    private int totalCoins;
    private ArrayList<CloudCard> cloudCards;
    private ArrayList<Color> unusedProfessors;
    boolean expertMode;

    public Game(int players) {
        if(players>3)
        {
            System.out.println("Maximum player count is 3, players have been set to 3");
            players = 3;
        }
        else if(players<2)
        {
            System.out.println("Minimum player count is 2, game has been set with 2 players");
            players=2;
        }
        islands = new ArrayList<>();

        for(int i = 0; i < MAX_ISLANDS; i++)
            islands.add(new IslandGroup());

        this.players = new ArrayList<>();
        for(int i=0;i < players; i++)
            this.players.add(new Player());

        //inizializzo unused professor
        unusedProfessors = new ArrayList<>();
        for(Color c : Color.values())
        unusedProfessors.add(c);

        //inizializzo bag
        bag= new EnumMap<>(Color.class);

        bag.put(Color.YELLOW,26);
        bag.put(Color.RED,26);
        bag.put(Color.GREEN,26);
        bag.put(Color.PINK,26);
        bag.put(Color.BLUE,26);

        //metodo scritto sotto che sceglie le 3 carte personaggio

       // characterCards= new ArrayList<>();

       // extract3CharacterCard();

    }

    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    public ArrayList<IslandGroup> getIslands() {
        return islands;
    }

    /**
     * merges the island in the given index with the next one
     * @param index index of the first island to merge
     * @throws IndexOutOfBoundsException when the given index exceets the maximum index
     */
    public void mergeIslands(int index) throws IndexOutOfBoundsException {
        IslandGroup first = islands.get(index);
        IslandGroup second;

        try {
            second = islands.get(index + 1);
        } catch (IndexOutOfBoundsException ex) {
            second = islands.get(0);
            index = 0;
        }

        IslandGroup merged = buildUnifiedIsland(first, second);

        islands.remove(first);
        islands.remove(second);

        islands.add(index, merged);
    }

    /**
     * returns a new island which is the merge of the two given
     * @param first first island to merge
     * @param second second island to merge
     * @return unified island
     */
    private IslandGroup buildUnifiedIsland(IslandGroup first, IslandGroup second) {
        IslandGroup result = new IslandGroup();

        for(Color c : Color.values()) {
            result.addStudents(c, first.getStudents(c) + second.getStudents(c));
        }

        result.incrementIslandCount(first.getIslandCount());
        result.incrementIslandCount(second.getIslandCount());

        return result;
    }


    /**
     * method extract from bag: it converts possible keys into numbers and extracts on of them by
     * decreasing the number from bag and increasing to extracted
     * @author Federico Lombardo
     * @param student describes how many students we need to extract
     * @return a new EnumMap with the extracted students
     */

    public EnumMap<Color,Integer> extractFromBag(int student){

        EnumMap<Color,Integer> extracted= new EnumMap<>(Color.class);

        for(int i=0;i<student;i++) {

            int extractcolor = new Random().nextInt(bag.values().size());

            if (bag.get(Color.values()[extractcolor]) > 0) {

                bag.put(Color.values()[extractcolor], bag.get(Color.values()[extractcolor]) - 1);
                if(extracted.get(Color.values()[extractcolor])!=null)
                extracted.put(Color.values()[extractcolor],extracted.get(Color.values()[extractcolor])+1);
                else extracted.put(Color.values()[extractcolor],1);
            }
        }
        return extracted;
    }

    /**
     * extracts one single student from the bag
     * @return color of the extracted student
     */
    public Color extractFromBag() {
        return extractFromBag(1).keySet().stream().findFirst().get();
    }


    /**
     * method addToBag if bag does not contains Color c then this is added, else
     * update the value
     * @author Federico Lombardo
     * @param students contains student that should be add to the bag
     */

    public void addToBag(EnumMap<Color, Integer> students) {

        for (Color c : students.keySet()) {

            if (!bag.containsKey(c))
                bag.put(c, students.get(c));
            else bag.put(c, bag.get(c) + students.get(c));

        }
    }

    /**
     * moves MN position by the given steps
     * @param steps steps to perform
     */
    public void moveMotherNature(int steps) {
        motherNaturePosition = (motherNaturePosition + steps) % islands.size();
    }

    public int getMotherNaturePosition() {
        return motherNaturePosition;
    }

    public int getCurrentPlayer(){return currentPlayer;}

    public ArrayList<CharacterCard> getCharacterCards() {
        return characterCards;
    }

    public ArrayList<CloudCard> getCloudCards() {
        return cloudCards;
    }

    /**
     * This method calculates the influence
     * @param p is the player
     * @param isl is the island
     * @return the calculation of the influence
     */

    public int countInfluence(Player p,IslandGroup isl){

        if(isl.isBlockColorOnce_CC()){
            int x=countInfluenceTowers(p,isl)+countInfluenceStudents(p,isl)-isl.getStudents(isl.getBlockedColor());
            isl.setBlockedColor(null);
            return x;
        }
        else if(isl.isPlus2Influence_CC()){
            isl.setPlus2Influence_CC(false);
            return countInfluenceStudents(p,isl)+countInfluenceTowers(p,isl)+2;
        }
        else if(isl.isNoEntryIsland()) {
            isl.setNoEntryIsland(false);
            throw new IllegalArgumentException("Influence cannot be calculated after activating the character card");
        }
        else if(isl.isBlockTower_CC()){
            return countInfluenceStudents(p,isl);
        }
        else return countInfluenceTowers(p,isl)+countInfluenceStudents(p,isl);

    }

    public int countInfluenceStudents(Player p,IslandGroup isl){
        int sum=0;
        for (Color c : isl.getStudents().keySet()){
            if(p.hasProfessor(c))
                sum=sum+isl.getStudents(c);
        }
        return sum;
    }

    public int countInfluenceTowers(Player p,IslandGroup isl){
        int sum=0;

        if(isl.getOccupiedBy().equals(p)){
            sum=sum+isl.getIslandCount();
        }
        return sum;
    }

    /**
     *
     * @return array with three different numbers from 0 to 11
     */

    private int[] extract3Numbers() {
        Random random = new Random();
        int tempAr[];
        tempAr = new int[3];
        int casuale = 0;

        casuale = random.nextInt(12);
        tempAr[0] = casuale +1;

        do {
            casuale = random.nextInt(12);
            tempAr[1] = casuale +1;

        } while (tempAr[1] == tempAr[0]);

        do {
            casuale = random.nextInt(12);
            tempAr[2] = casuale +1;

        } while (tempAr[2] == tempAr[1] || tempAr[2] == tempAr[0]);

        return tempAr;
    }

    private void extract3CharacterCard(){
        ArrayList<CharacterCard> allCharacterCards= new ArrayList<CharacterCard>();
        int extracted[];

        //estraggo 3 numeri e li metto in array extracted
        extracted=extract3Numbers();

        //eseguo il ciclo 3 volte per generare i 3 CharacterCards
        for(int i = 0;i<3;i++){
            switch (extracted[i]){
                case 1:
                    characterCards.add(new Choose1ToIsland());
                case 2:
                    characterCards.add(new TempControlProf());
                case 3:
                    characterCards.add(new ChooseIsland());
                case 4:
                    characterCards.add(new BlockTower());
                case 5:
                    characterCards.add(new NoEntryIsland());
                case 6:
                    characterCards.add(new TwoAdditionalMoves());
                case 7:
                    characterCards.add(new Choose3toEntrance());
                case 8:
                    characterCards.add(new Plus2Influence());
                case 9:
                    characterCards.add(new BlockColorOnce());
                case 10:
                    characterCards.add(new Exchange2Students());
                case 11:
                    characterCards.add(new Choose1DiningRoom());
                case 12:
                    characterCards.add(new AllRemoveColor());
            }
        }

    }



    public int getTotalCoins() {
        return totalCoins;
    }

    public void decreaseTotalCoins(){
        totalCoins=totalCoins-1;
    }

    public ArrayList<Color> getUnusedProfessors() {
        return unusedProfessors;
    }

    public void addCharacterCard(CharacterCard c){
        characterCards.add(c);

    }
}

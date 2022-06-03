package it.polimi.ingsw.model;

import it.polimi.ingsw.model.charactercards.*;
import it.polimi.ingsw.network.message.Lobby;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.view.cli.ColorCli;

import java.util.*;
import java.util.stream.Collectors;

public class Game extends Observable {
    private final EnumMap<Color, Integer> bag;
    private int motherNaturePosition = 0;
    private final int MAX_ISLANDS = 12;
    private final int MAX_BAG_STUDENTS = 130;
    private final ArrayList<IslandGroup> islands;
    private final ArrayList<Player> players;
    private int currentPlayer;
    private ArrayList<CharacterCard> characterCards;
    private int totalCoins;
    private final ArrayList<CloudCard> cloudCards;
    private final ArrayList<Color> unusedProfessors;
    private final boolean expertMode;
    private final int maxPlayers;


    public Game(int players, boolean expertMode) {
        Random rand = new Random();
        maxPlayers = players;

        // unused professor init
        unusedProfessors = new ArrayList<>();
        unusedProfessors.addAll(Arrays.asList(Color.values()));

        // bag init
        bag = new EnumMap<>(Color.class);
        for (Color c : Color.values())
            bag.put(c, MAX_BAG_STUDENTS / Color.values().length);

        // isalnds init
        islands = new ArrayList<>();
        for(int i = 0; i < MAX_ISLANDS; i++) {
            IslandGroup ig = new IslandGroup();
            if (i != MAX_ISLANDS / 2 - 1 && i != 0) {
                Color color = Color.values()[rand.nextInt(Color.values().length)];
                bag.remove(color, 1);
                ig.addStudents(color, 1);
            }

            islands.add(ig);
        }

        // players and dashboard init
        this.players = new ArrayList<>();

        // expertMode init
        if (expertMode) {
            characterCards = new ArrayList<>();
            characterCards.add(new AllRemoveColor(this));
            characterCards.add(new BlockColorOnce(this));
            characterCards.add(new Choose1ToIsland(this));
            //extract3CharacterCard();
        }

        currentPlayer = rand.nextInt(players);

        //sets game mode
        this.expertMode = expertMode;

        // cloudCards init
        cloudCards = new ArrayList<>();
        for (int i = 0; i < players; i++) {
            CloudCard c = new CloudCard(this);
            this.cloudCards.add(c);
        }
    }

    public void addPlayer(String nickname) {
        Player p = new Player();
        p.setCurrentGame(this);
        p.getSchoolDashboard().setCurrentGame(this);
        p.getSchoolDashboard().setUp();
        p.setNickname(nickname);
        if (expertMode)
            p.setCoins(20);
        this.players.add(p);

        List<String> nicknames = players.stream().map(Player::getNickname).collect(Collectors.toList());
        notifyObservers(new Lobby(nicknames, getMaxPlayers()));
    }

    public boolean isExpertMode() {
        return  expertMode;
    }

    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    public List<Player> getPlayersExceptCurrent() {
        return players.stream().filter(p -> !p.equals(getCurrentPlayerInstance())).toList();
    }



    /**
     * Gets the current number of players in the game.
     *
     * @return number of players currently in the game.
     */
    public int getPlayersCount() {
        return players.size();
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public ArrayList<IslandGroup> getIslands() {
        return islands;
    }

    /**
     * Merges the island in the given index with the next one.
     * @param index Index of the first island to merge.
     * @throws IndexOutOfBoundsException When the given index exceeds the maximum index.
     */
    public void mergeIslands(int index) throws IndexOutOfBoundsException {
        if (index == motherNaturePosition - 1)
            motherNaturePosition -= 1;

        IslandGroup first = islands.get(index);
        IslandGroup second;

        try {
            second = islands.get(index + 1);
        } catch (IndexOutOfBoundsException ex) {
            second = islands.get(0);
        }

        first.merge(second);

        islands.remove(second);
    }

    /**
     * method extract from bag: it converts possible keys into numbers and extracts one of them by
     * decreasing the number from bag and increasing to extracted.
     * If there are not enough students throw IllegalArgumentException
     * @param student describes how many students we need to extract
     * @return a new EnumMap with the extracted students
     */

    public EnumMap<Color,Integer> extractFromBag(int student) {
        EnumMap<Color,Integer> extracted = new EnumMap<>(Color.class);
        if(studentsInBag()>0 && studentsInBag()>=student){
            for (int i = 0; i < student; i++) {

                int extractColor = new Random().nextInt(bag.values().size());

                if (bag.get(Color.values()[extractColor]) > 0) {

                    bag.put(Color.values()[extractColor], bag.get(Color.values()[extractColor]) - 1);
                    if (extracted.get(Color.values()[extractColor]) != null)
                        extracted.put(Color.values()[extractColor], extracted.get(Color.values()[extractColor]) + 1);
                    else extracted.put(Color.values()[extractColor], 1);
                }else i--;
            }
            return extracted;
        }
        else throw new  IllegalArgumentException("Not enough students");
    }

    public int studentsInBag(Color color) {
        return bag.getOrDefault(color, 0);
    }

    public int studentsInBag() {
        int result = 0;
        for (Color c : bag.keySet())
            result += studentsInBag(c);
        return result;
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

    public IslandGroup getCurrentIsland() {
        return islands.get(motherNaturePosition);
    }

    public IslandGroup getPreviousIsland() {
        return islands.get(Math.floorMod((motherNaturePosition - 1), islands.size()));
    }

    public IslandGroup getNextIsland() {
        return islands.get((motherNaturePosition + 1) % islands.size());
    }

    public IslandGroup getMotherNatureIsland() {
        return islands.get(motherNaturePosition);
    }

    public int getCurrentPlayer(){return currentPlayer;}

    public Player getCurrentPlayerInstance() {
        return players.get(currentPlayer);
    }

    public String getCurrentPlayerNick() {
        return getCurrentPlayerInstance().getNickname();
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ArrayList<CharacterCard> getCharacterCards() {
        return characterCards;
    }

    public CharacterCard getCharacterCard(int index) {
        return characterCards.get(index);
    }

    public ArrayList<CloudCard> getCloudCards() {
        return cloudCards;
    }

    /**
     * This method calculates the influence
     * @param player is the player
     * @param isl is the island
     * @return the calculation of the influence
     */

    public int countInfluence(Player player, IslandGroup isl){
        if(expertMode) {
            if (isl.isBlockColorOnce_CC()) {
                int x = countInfluenceTowers(player, isl) + countInfluenceStudents(player, isl) - isl.getStudents(isl.getBlockedColor());
                isl.setBlockedColor(null);
                isl.setBlockColorOnce_CC(false);
                return x;
            } else if (isl.isPlus2Influence_CC()) {
                isl.setPlus2Influence_CC(false);
                return countInfluenceStudents(player, isl) + countInfluenceTowers(player, isl) + 2;
            } else if (isl.isNoEntryIsland()) {
                isl.setNoEntryIsland(false);
                return 0;
            } else if (isl.isBlockTower_CC()) {
                isl.setBlockTower_CC(false);
                return countInfluenceStudents(player, isl);
            }
            else return countInfluenceTowers(player,isl)+countInfluenceStudents(player,isl);
        }
        else return countInfluenceTowers(player,isl)+countInfluenceStudents(player,isl);

    }

    private int countInfluenceStudents(Player p, IslandGroup isl){
        int sum=0;
        for (Color c : isl.getStudents().keySet()){
            if(p.getSchoolDashboard().getProfessors().contains(c))
                sum=sum+isl.getStudents(c);
        }
        return sum;
    }

    private int countInfluenceTowers(Player p, IslandGroup isl){
        int sum=0;

        if(isl.getOccupiedBy() == null)
            return 0;

        if(isl.getOccupiedBy().equals(p))
            sum = isl.getIslandCount();

        return sum;
    }

    /**
     *
     * @return array with three different numbers from 0 to 11
     */

    private int[] extract3Numbers() {
        Random random = new Random();
        int[] tempAr;
        tempAr = new int[3];
        int randomNumber = 0;

        randomNumber = random.nextInt(12);
        tempAr[0] = randomNumber +1;

        do {
            randomNumber = random.nextInt(12);
            tempAr[1] = randomNumber +1;

        } while (tempAr[1] == tempAr[0]);

        do {
            randomNumber = random.nextInt(12);
            tempAr[2] = randomNumber +1;

        } while (tempAr[2] == tempAr[1] || tempAr[2] == tempAr[0]);

        return tempAr;
    }

    public void extract3CharacterCard(){
        ArrayList<CharacterCard> allCharacterCards= new ArrayList<>();
        int[] extracted;

        // extract 3 random numbers
        extracted=extract3Numbers();

        // loop through extracted numbers
        for(int i = 0;i<3;i++){
            switch (extracted[i]) {
                case 1 -> characterCards.add(new Choose1ToIsland(this));
                case 2 -> characterCards.add(new TempControlProf(this));
                case 3 -> characterCards.add(new ChooseIsland(this));
                case 4 -> characterCards.add(new BlockTower(this));
                case 5 -> characterCards.add(new NoEntryIsland(this));
                case 6 -> characterCards.add(new TwoAdditionalMoves(this));
                case 7 -> characterCards.add(new Choose3toEntrance(this));
                case 8 -> characterCards.add(new Plus2Influence(this));
                case 9 -> characterCards.add(new BlockColorOnce(this));
                case 10 -> characterCards.add(new Exchange2Students(this));
                case 11 -> characterCards.add(new Choose1DiningRoom(this));
                case 12 -> characterCards.add(new AllRemoveColor(this));
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

    /**
     * Adds a professor to the unused professors of the game, if it is not already present.
     * @param color color of the professor to be added.
     */
    public void addUnusedProfessor(Color color) {
        if (! unusedProfessors.contains(color))
            unusedProfessors.add(color);
    }

    /**
     * Removes a professor from the unused professors of the game, if present.
     * @param color color of the professor to be removed.
     */
    public void removeUnusedProfessor(Color color) {
        unusedProfessors.remove(color);
    }


    /**
     * calculates the player with maximum influence, that is the eligible owner for islandGroup
     *
     * @param islandGroup island to compute influence on
     * @return player instance with maximum influence for the given island or null if two players have maximum influence
     */
    public Player playerWithHigherInfluence(IslandGroup islandGroup) {
        Map<Integer, ArrayList<Player>> influenceMap = new HashMap<>();
        Player result = null;

        for (Player p : players) {
            int currentInfluence = countInfluence(p, islandGroup);
            ArrayList<Player> players = influenceMap.get(currentInfluence);

            ArrayList<Player> toAdd = new ArrayList<>();
            if (players == null) {
                toAdd.add(p);
            } else {
                toAdd = influenceMap.get(currentInfluence);
                toAdd.add(p);
            }

            influenceMap.put(currentInfluence, toAdd);
        }

        int maxInfluence = Collections.max(influenceMap.keySet());

        int tiedPlayersCount = influenceMap.get(maxInfluence).size();

        if (tiedPlayersCount == 1)
            result = influenceMap.get(maxInfluence).get(0);

        return result;
    }

    /**
     * finds the player with the highest amount of towers used and, if tied, the player with the most professors
     * @return winning player
     */
    public Player winner()
    {
        Player winner=players.get(0);

        //finds the player with the lowest number of towers in their school dashboard
        for(int i=1;i<players.size();i++)
        {
            if(players.get(i).getSchoolDashboard().getTowers()<winner.getSchoolDashboard().getTowers())
            {
                winner=players.get(i);
            }
        }


        //checks if there is any other player with the same number of towers (tie), if there is, winner is the player with the highest amount of professors
        for(Player p : players)
        {
            if(p!=winner)
            {
                if(p.getSchoolDashboard().getTowers()==winner.getSchoolDashboard().getTowers())
                {

                    if(p.getSchoolDashboard().getProfessors().size()>winner.getSchoolDashboard().getProfessors().size())
                    {
                        winner=p;
                    }
                }
            }
        }

        return winner;
    }

    /**
     * @return List of the integers indexes of the cloud cards which are full.
     */
    public List<Integer> getPlayableCloudCards() {
        List<Integer> result = new ArrayList<>();

        int i = 0;
        for (CloudCard cc : cloudCards) {
            if (! cc.getStudents().isEmpty())
                result.add(i);

            i++;
        }

        return result;
    }

    /**
     * Allows to get a Character Card of the game based on its {@link CharacterCardType}.
     * @param type {@link CharacterCardType} of the character card to retrieve.
     * @return {@link CharacterCard} corresponding to the given type.
     * @throws IllegalArgumentException if the given type does not match any character card in the game instance.
     */
    public CharacterCard getCharacterCard(CharacterCardType type) throws IllegalArgumentException {
        int index = -1;

        for (int i = 0; i < characterCards.size(); i++) {
            if (characterCards.get(i).getType() == type) {
                index = i;
                break;
            }
        }

        if (index == -1)
            throw new IllegalArgumentException("Given type is not present in this game instance.");
        else
            return characterCards.get(index);
    }

}
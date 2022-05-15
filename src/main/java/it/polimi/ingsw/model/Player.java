package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;

public class Player {
    private int coins;
    private Hand hand;
    private SchoolDashboard schoolDashboard;
    private ArrayList<AssistantCard> discardPile;
    private int cardValue;
    private int maxPosition;
    private Game currentGame;
    private int count;
    private ArrayList<Color> professors;



    /** each player starts with a hand of 10 assistant cards, 10 coins and a school dashboard. Each player is identified by his/her nickname**/
    public Player()
    {
        hand = new Hand();
        schoolDashboard = new SchoolDashboard();
        for(Color c: Color.values())
            schoolDashboard.getDiningRoom().put(c,0);

        coins = 0;
        maxPosition = 0;
        professors = new ArrayList<>();
    }

    public void setCurrentGame(Game game)
    {
        this.currentGame=game;
    }

    public int getCardValue()
    {
        return cardValue;
    }

    public int getMaxPosition() {
        return maxPosition;
    }

    //aggiunta io
    public void setMaxPosition(int maxPosition){
        this.maxPosition=maxPosition;
    }

    //aggiunta io
    public SchoolDashboard getSchoolDashboard(){return schoolDashboard; }

    public void addProfessor(Color c){
        professors.add(c);
    }
    public void removeProfessor(Color c){
        professors.add(c);

    }

    /**
     * Player chooses the assistant card to play, setting the maximum position that mother nature can move and the priority. It gets placed in the discard pile.
     * @param i, card number i from the left in hand
     * @throws IndexOutOfBoundsException if i is outside the possible range of values
     */
    public void chooseAssistantCard(int i) throws IndexOutOfBoundsException
    {
        maxPosition = hand.assistantCards.get(i).getMaxSteps();
        cardValue = hand.assistantCards.get(i).getCardValue();
        discardPile.add(hand.assistantCards.get(i));
        hand.assistantCards.remove(i);
    }
    public void moveOneOfThreeToIsland(Color color, int i) throws NullPointerException, IllegalArgumentException
    {
        try {
            if(count>=3)throw new IllegalStateException("limit of students moved reached for this turn");
            schoolDashboard.moveToIslandGroup(color, i);
        }
        catch (IndexOutOfBoundsException e)
        {
            System.out.println("Chosen island is outside the possible range of values");
        }
        finally{
            count++;
        }


    }
    public void moveOneOfThreeToDiningRoom(Color color) throws NullPointerException, IllegalArgumentException
    {
        schoolDashboard.moveStudentToDiningRoom(color);
        count++;

    }


    public boolean hasProfessor(Color c) {
        for (Player p : currentGame.getPlayers()) {
            SchoolDashboard sd = p.getSchoolDashboard();
            int students = sd.getDiningRoom().get(c);
            if(students > schoolDashboard.getDiningRoom().get(c))
                return false;
        }

        return true;
        //return professors.contains(c);
    }

    public void chooseCharacterCard(int i){


    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}

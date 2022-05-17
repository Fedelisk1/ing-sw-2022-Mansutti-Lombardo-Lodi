package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;

public class Player {
    private int coins;
    private Hand hand;
    private String nickname;
    private SchoolDashboard schoolDashboard;
    private ArrayList<AssistantCard> discardPile;
    private int cardValue;
    private int maxSteps;
    private Game currentGame;
    private int count;


    /** each player starts with a hand of 10 assistant cards, 10 coins and a school dashboard. Each player is identified by his/her nickname**/
    public Player()
    {
        hand = new Hand();
        schoolDashboard = new SchoolDashboard();
        for(Color c: Color.values())
            schoolDashboard.getDiningRoom().put(c,0);

        coins = 0;
        discardPile=new ArrayList<>();
    }

    public String getNickname() {
        return nickname;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void setCurrentGame(Game game)
    {
        this.currentGame=game;
    }

    public int getCardValue()
    {
        return cardValue;
    }

    public int getMaxSteps() {
        return maxSteps;
    }

    //aggiunta io
    public void setMaxSteps(int maxSteps){
        this.maxSteps=maxSteps;
    }

    //aggiunta io
    public SchoolDashboard getSchoolDashboard(){return schoolDashboard; }


    /**
     * Player chooses the assistant card to play, setting the maximum position that mother nature can move and the priority. It gets placed in the discard pile.
     * @param i, card number i from the left in hand
     * @throws IndexOutOfBoundsException if i is outside the possible range of values
     */
    public void chooseAssistantCard(int i) throws IndexOutOfBoundsException
    {
        maxSteps = hand.assistantCards.get(i).getMaxSteps();
        cardValue = hand.assistantCards.get(i).getCardValue();
        discardPile.add(hand.assistantCards.get(i));
        hand.assistantCards.remove(i);
    }


    public void chooseCloudCard(int i){
        currentGame.getCloudCards().get(i).transferStudents();
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
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

    public void resetCount()
    {
        count=0;
    }
    public void moveOneOfThreeToDiningRoom(Color color) throws NullPointerException, IllegalArgumentException
    {
        if(count==3)throw new IllegalStateException("limit of students moved reached for this turn");
        schoolDashboard.moveStudentToDiningRoom(color);
        count++;

    }
}
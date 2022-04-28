package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Player {
    private String nickname;
    private int coins;
    private Hand hand;
    private SchoolDashboard schoolDashboard;
    private ArrayList<AssistantCard> discardPile;
    private int playedPriority;
    private int maxPosition;
    private Game currentGame;

    /** each player starts with a hand of 10 assistant cards, 10 coins and a school dashboard. Each player is identified by his/her nickname**/
    public Player(String nickname)
    {
        hand = new Hand();
        schoolDashboard = new SchoolDashboard();
        coins = 0;
        this.nickname = nickname;

    }

    public void setCurrentGame(Game game)
    {
        this.currentGame=game;
    }

    public int getPlayedPriority()
    {
        return playedPriority;
    }

    public int getMaxPosition() {
        return maxPosition;
    }

    /**plays card number i from the hand**/
    public void playAssistantCard(int i)
    {
        try{
            maxPosition = hand.assistantCards.get(i).getMaxSteps();
            playedPriority = hand.assistantCards.get(i).getPriority();
            discardPile.add(hand.assistantCards.get(i));
            hand.assistantCards.remove(i);

        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Assistant card not valid");
        }
    }




}

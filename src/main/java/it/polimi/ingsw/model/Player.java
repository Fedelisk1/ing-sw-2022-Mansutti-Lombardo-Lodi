package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Player {
    private String nickname;
    private int coins;
    private Hand hand;
    private SchoolDashboard schoolDashboard;
    private int PlayedPriority;
    private int MaxPosition;

    /** each player starts with a hand of 10 assistant cards, 10 coins and a school dashboard. Each player is identified by his/her nickname**/
    public Player(String nickname)
    {
        hand = new Hand();
        schoolDashboard = new SchoolDashboard();
        coins = 0;
        this.nickname = nickname;

    }
    /**plays card number i from the hand**/
    public void playAssistantCard(int i)
    {
        try{
            MaxPosition = hand.assistantCards.get(i).getMaxSteps();
            PlayedPriority = hand.assistantCards.get(i).getPriority();
            hand.assistantCards.remove(i    );
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Assistant card not valid");
        }
    }
}

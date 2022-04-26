package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Hand {
    private ArrayList<AssistantCard> hand;

    /**initialising player's starting hand**/
    public Hand()
    {

        hand = new ArrayList<>();
        hand.add(new AssistantCard(1,1));
        hand.add(new AssistantCard(2,1));
        hand.add(new AssistantCard(3,2));
        hand.add(new AssistantCard(4,2));
        hand.add(new AssistantCard(5,3));
        hand.add(new AssistantCard(6,3));
        hand.add(new AssistantCard(7,4));
        hand.add(new AssistantCard(8,4));
        hand.add(new AssistantCard(9,5));
        hand.add(new AssistantCard(10,5));

    }
}

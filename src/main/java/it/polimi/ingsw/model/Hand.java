package it.polimi.ingsw.model;
import java.util.ArrayList;

public class Hand {
    public ArrayList<AssistantCard> assistantCards;

    /**initialising player's starting assistantCards**/
    public Hand()
    {

        assistantCards = new ArrayList<>();
        assistantCards.add(new AssistantCard(1,1));
        assistantCards.add(new AssistantCard(2,1));
        assistantCards.add(new AssistantCard(3,2));
        assistantCards.add(new AssistantCard(4,2));
        assistantCards.add(new AssistantCard(5,3));
        assistantCards.add(new AssistantCard(6,3));
        assistantCards.add(new AssistantCard(7,4));
        assistantCards.add(new AssistantCard(8,4));
        assistantCards.add(new AssistantCard(9,5));
        assistantCards.add(new AssistantCard(10,5));

    }

}

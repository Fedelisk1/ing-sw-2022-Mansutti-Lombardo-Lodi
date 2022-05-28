package it.polimi.ingsw.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Hand {
    private ArrayList<AssistantCard> assistantCards;

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

    public ArrayList<AssistantCard> getAssistantCards() {
        return assistantCards;
    }

    /**
     * Returns player's card values as list.
     * @return List of player's card values.
     */
    public List<Integer> getAssistantCardsAsList() {
        return assistantCards.stream().map(AssistantCard::getPriority).toList();
    }

    /**
     * Returns assistant cards as Integer to Integer Map.
     *
     * @return map representing assistant cards.
     */
    public Map<Integer, Integer> getAsMap() {
        return assistantCards.stream().collect(Collectors.toMap(AssistantCard::getPriority, AssistantCard::getMaxSteps));
    }
}

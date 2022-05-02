package it.polimi.ingsw.model;

public class AssistantCard {
    private int cardValue;
    private int maxSteps;

    public AssistantCard(int cardValue, int maxSteps)
    {
        this.cardValue = cardValue;
        this.maxSteps = maxSteps;
    }

    public int getCardValue() {
        return cardValue;
    }

    public int getMaxSteps() {
        return maxSteps;
    }
}

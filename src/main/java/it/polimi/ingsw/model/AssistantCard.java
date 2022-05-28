package it.polimi.ingsw.model;

public class AssistantCard {
    private final int cardValue;
    private final int maxSteps;

    public AssistantCard(int cardValue, int maxSteps)
    {
        this.cardValue = cardValue;
        this.maxSteps = maxSteps;
    }

    public int getPriority() {
        return cardValue;
    }

    public int getMaxSteps() {
        return maxSteps;
    }
}

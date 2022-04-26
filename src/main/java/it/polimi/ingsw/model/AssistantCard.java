package it.polimi.ingsw.model;

public class AssistantCard {
    private int priority;
    private int maxSteps;

    public AssistantCard(int priority, int maxSteps)
    {
        this.priority = priority;
        this.maxSteps = maxSteps;
    }

    public int getPriority() {
        return priority;
    }

    public int getMaxSteps() {
        return maxSteps;
    }
}

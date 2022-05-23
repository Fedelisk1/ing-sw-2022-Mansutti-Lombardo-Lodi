package it.polimi.ingsw.network.message;

public class MoveMotherNature extends Message{

    int steps;

    public MoveMotherNature(String nickname,int steps)
    {
        super(nickname,MessageType.MOVE_MOTHER_NATURE);
        this.steps=steps;
    }

    public int getSteps() {
        return steps;
    }
}

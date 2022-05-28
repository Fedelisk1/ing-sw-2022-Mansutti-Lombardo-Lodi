package it.polimi.ingsw.network.message;

import java.io.Serial;

public class MoveMotherNature extends Message{

    @Serial
    private static final long serialVersionUID = 568710680915970624L;
    private final int steps;

    public MoveMotherNature(String nickname,int steps)
    {
        super(nickname,MessageType.MOVE_MOTHER_NATURE);
        this.steps=steps;
    }

    public int getSteps() {
        return steps;
    }
}

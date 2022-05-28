package it.polimi.ingsw.network.message;

import java.io.Serial;

public class AskActionPhase2 extends Message {

    @Serial
    private static final long serialVersionUID = 1619810305158154805L;
    private final int maxMNSteps;
    public AskActionPhase2(int maxMNSteps) {
        super(SERVER_NICKNAME, MessageType.ASK_ACTION_PHASE_2);
        this.maxMNSteps = maxMNSteps;
    }

    public int getMaxMNSteps() {
        return maxMNSteps;
    }
}

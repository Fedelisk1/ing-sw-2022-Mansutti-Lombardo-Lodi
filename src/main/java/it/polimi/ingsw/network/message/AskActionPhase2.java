package it.polimi.ingsw.network.message;

import java.io.Serial;

public class AskActionPhase2 extends Message {

    @Serial
    private static final long serialVersionUID = 1619810305158154805L;
    private final int maxMNSteps;
    private final boolean expert;

    public AskActionPhase2(int maxMNSteps, boolean expert) {
        super(SERVER_NICKNAME, MessageType.ASK_ACTION_PHASE_2);
        this.maxMNSteps = maxMNSteps;
        this.expert = expert;
    }

    public int getMaxMNSteps() {
        return maxMNSteps;
    }

    public boolean isExpert() {
        return expert;
    }
}

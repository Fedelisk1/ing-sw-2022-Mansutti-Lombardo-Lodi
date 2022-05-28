package it.polimi.ingsw.network.message;

import java.io.Serial;

public class AskActionPhase1 extends Message{
    @Serial
    private static final long serialVersionUID = 4137685492495730196L;
    private final int count;
    private final int maxIsland;

    public AskActionPhase1(int count, int maxIsland) {
        super(SERVER_NICKNAME, MessageType.ASK_ACTION_PHASE_1);
        this.count = count;
        this.maxIsland = maxIsland;
    }

    public int getCount() {
        return count;
    }

    public int getMaxIsland() {
        return maxIsland;
    }
}

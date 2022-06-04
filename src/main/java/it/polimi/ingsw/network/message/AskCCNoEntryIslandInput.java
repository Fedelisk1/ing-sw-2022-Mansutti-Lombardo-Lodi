package it.polimi.ingsw.network.message;

import java.io.Serial;

public class AskCCNoEntryIslandInput extends Message {

    @Serial
    private static final long serialVersionUID = 1149230936659388218L;
    private final int maxIsland;

    public AskCCNoEntryIslandInput(int maxIsland) {
        super(SERVER_NICKNAME, MessageType.ASK_CC_NO_ENTRY_ISLAND_INPUT);

        this.maxIsland = maxIsland;
    }

    public int getMaxIsland() {
        return maxIsland;
    }
}

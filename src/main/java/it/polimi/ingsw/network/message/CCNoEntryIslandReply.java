package it.polimi.ingsw.network.message;

import java.io.Serial;

public class CCNoEntryIslandReply extends Message{
    @Serial
    private static final long serialVersionUID = 3911259759538721835L;
    private final int island;

    public CCNoEntryIslandReply(String nickname, int island) {
        super(nickname, MessageType.CC_NO_ENTRY_ISLAND_REPLY);
        this.island=island;
    }

    public int getIsland() {
        return island;
    }
}

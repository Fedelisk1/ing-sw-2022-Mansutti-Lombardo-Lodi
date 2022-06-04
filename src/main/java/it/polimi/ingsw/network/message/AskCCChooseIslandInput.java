package it.polimi.ingsw.network.message;

import java.io.Serial;

public class AskCCChooseIslandInput extends Message{

    @Serial
    private static final long serialVersionUID = -7043717916886337470L;
    private final int maxIsland;

    public AskCCChooseIslandInput(int maxIsland) {
        super(SERVER_NICKNAME, MessageType.ASK_CC_CHOOSE_ISLAND_INPUT);
        this.maxIsland = maxIsland;
    }

    public int getMaxIsland() {
        return maxIsland;
    }
}


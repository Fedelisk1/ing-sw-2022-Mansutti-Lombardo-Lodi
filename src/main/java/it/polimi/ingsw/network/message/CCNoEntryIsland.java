package it.polimi.ingsw.network.message;

import java.io.Serial;

public class CCNoEntryIsland extends Message{
    @Serial
    private static final long serialVersionUID = 3911259759538721835L;
    private final int islNumb;
    private final int cardPosition;

    public CCNoEntryIsland(String nickname, int islNumb, int cardPosition) {
        super(nickname, MessageType.CC_NO_ENTRY_ISLAND);
        this.islNumb=islNumb;
        this.cardPosition=cardPosition;
    }

    public int getIslNumb() {
        return islNumb;
    }

    public int getCardPosition() {
        return cardPosition;
    }
}

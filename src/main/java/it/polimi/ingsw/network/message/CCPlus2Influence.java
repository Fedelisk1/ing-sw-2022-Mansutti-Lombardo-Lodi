package it.polimi.ingsw.network.message;

import java.io.Serial;

public class CCPlus2Influence extends Message{
    @Serial
    private static final long serialVersionUID = -8601977952889452611L;
    private final int cardPosition;

    public CCPlus2Influence(String nickname, int cardPosition) {
        super(nickname, MessageType.CC_PLUS_2_INFLUENCE);
        this.cardPosition=cardPosition;
    }

    public int getCardPosition() {
        return cardPosition;
    }
}

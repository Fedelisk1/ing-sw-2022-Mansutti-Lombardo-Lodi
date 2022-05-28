package it.polimi.ingsw.network.message;

import java.io.Serial;

public class CCTwoAdditionalMoves extends Message{
    @Serial
    private static final long serialVersionUID = 5816404655753525636L;
    private final int cardPosition;

    public CCTwoAdditionalMoves(String nickname, int cardPosition) {
        super(nickname, MessageType.CC_TWO_ADDITIONAL_MOVES);
        this.cardPosition=cardPosition;
    }

    public int getCardPosition() {
        return cardPosition;
    }
}

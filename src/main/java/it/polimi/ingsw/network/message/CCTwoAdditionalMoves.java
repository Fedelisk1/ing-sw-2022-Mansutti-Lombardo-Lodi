package it.polimi.ingsw.network.message;

public class CCTwoAdditionalMoves extends Message{
    private MessageType messageType;
    private String nickname;
    private int cardPosition;

    public CCTwoAdditionalMoves(String nickname, MessageType messageType, int cardPosition) {
        super(nickname, MessageType.CC_TWO_ADDITIONAL_MOVES);
        this.cardPosition=cardPosition;
    }

    public int getCardPosition() {
        return cardPosition;
    }
}

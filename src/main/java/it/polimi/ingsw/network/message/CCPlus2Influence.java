package it.polimi.ingsw.network.message;

public class CCPlus2Influence extends Message{
    private MessageType messageType;
    private String nickname;
    private int cardPosition;

    public CCPlus2Influence(String nickname, MessageType messageType, int cardPosition) {
        super(nickname, MessageType.CC_PLUS_2_INFLUENCE);
        this.cardPosition=cardPosition;
    }

    public int getCardPosition() {
        return cardPosition;
    }
}

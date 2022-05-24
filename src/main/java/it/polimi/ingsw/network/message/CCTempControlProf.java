package it.polimi.ingsw.network.message;

public class CCTempControlProf extends Message{
    private MessageType messageType;
    private String nickname;
    private int cardPosition;

    public CCTempControlProf(String nickname, MessageType messageType, int cardPosition) {
        super(nickname, MessageType.CC_TEMP_CONTROL_PROF);
        this.cardPosition=cardPosition;
    }

    public int getCardPosition() {
        return cardPosition;
    }
}

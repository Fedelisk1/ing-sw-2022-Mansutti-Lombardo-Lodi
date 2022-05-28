package it.polimi.ingsw.network.message;

import java.io.Serial;

public class CCTempControlProf extends Message{
    @Serial
    private static final long serialVersionUID = -9192926679572515184L;
    private final int cardPosition;

    public CCTempControlProf(String nickname, int cardPosition) {
        super(nickname, MessageType.CC_TEMP_CONTROL_PROF);
        this.cardPosition=cardPosition;
    }

    public int getCardPosition() {
        return cardPosition;
    }
}

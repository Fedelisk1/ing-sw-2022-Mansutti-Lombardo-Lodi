package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

public class CCBlockTower extends Message{

    private MessageType messageType;
    private String nickname;
    private int cardPosition;

    public CCBlockTower(String nickname, MessageType messageType, int cardPosition){
        super(nickname, MessageType.CC_BLOCK_TOWER);
        this.cardPosition=cardPosition;
    }

    public int getCardPosition() {
        return cardPosition;
    }
}

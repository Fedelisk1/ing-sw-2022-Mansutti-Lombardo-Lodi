package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;

public class CCBlockTower extends Message{

    @Serial
    private static final long serialVersionUID = 7436449776216444681L;
    private final int cardPosition;

    public CCBlockTower(String nickname, int cardPosition){
        super(nickname, MessageType.CC_BLOCK_TOWER);
        this.cardPosition=cardPosition;
    }

    public int getCardPosition() {
        return cardPosition;
    }
}

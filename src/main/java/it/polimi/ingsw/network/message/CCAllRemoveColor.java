package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

public class CCAllRemoveColor extends Message{

    private MessageType messageType;
    private String nickname;
    private Color color;
    private int cardPosition;

    public CCAllRemoveColor(String nickname, MessageType messageType, Color color, int cardPosition) {
        super(nickname, MessageType.CC_ALL_REMOVE_COLOR);
        this.color=color;
        this.cardPosition=cardPosition;
    }

    public Color getColor() {
        return color;
    }

    public int getCardPosition() {
        return cardPosition;
    }
}

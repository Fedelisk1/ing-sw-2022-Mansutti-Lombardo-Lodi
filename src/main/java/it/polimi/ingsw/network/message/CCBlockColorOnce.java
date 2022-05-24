package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

public class CCBlockColorOnce extends Message {

    private MessageType messageType;
    private String nickname;
    private Color color;
    private int cardPosition;

    public CCBlockColorOnce(String nickname, MessageType messageType, Color color, int cardPosition) {
        super(nickname, MessageType.CC_BLOCK_COLOR_ONCE);
        this.color = color;
        this.cardPosition = cardPosition;
    }

    public Color getColor() {
        return color;
    }

    public int getCardPosition() {
        return cardPosition;
    }
}

package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;

public class CCBlockColorOnce extends Message {

    @Serial
    private static final long serialVersionUID = -7417154069659059419L;
    private final Color color;
    private final int cardPosition;

    public CCBlockColorOnce(String nickname, Color color, int cardPosition) {
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

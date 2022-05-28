package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;

public class CCAllRemoveColor extends Message{

    @Serial
    private static final long serialVersionUID = -1615577338990425315L;
    private final Color color;
    private final int cardPosition;

    public CCAllRemoveColor(String nickname, Color color, int cardPosition) {
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

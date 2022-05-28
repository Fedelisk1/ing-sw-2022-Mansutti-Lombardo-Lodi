package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;

public class CCChoose1DiningRoom extends Message {

    @Serial
    private static final long serialVersionUID = -165018580165269740L;
    private final Color color;
    private final int cardPosition;

    public CCChoose1DiningRoom(String nickname, Color color, int cardPosition) {
        super(nickname, MessageType.CC_CHOOSE_1_DINING_ROOM);
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

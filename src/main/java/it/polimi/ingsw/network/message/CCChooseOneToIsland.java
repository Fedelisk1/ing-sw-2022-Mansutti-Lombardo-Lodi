package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;

public class CCChooseOneToIsland extends Message{
    @Serial
    private static final long serialVersionUID = -5532482880285221121L;
    private final int islandNumber;
    private final int cardPosition;
    private final Color c;

    public CCChooseOneToIsland(String nickname, Color c, int islandNumber, int cardPosition) {
        super(nickname, MessageType.CC_CHOOSE_1_TO_ISLAND);
        this.c=c;
        this.islandNumber=islandNumber;
        this.cardPosition=cardPosition;
    }

    public int getIslandNumber() {
        return islandNumber;
    }

    public Color getColor() {
        return c;
    }

    public int getCardPosition() {
        return cardPosition;
    }
}

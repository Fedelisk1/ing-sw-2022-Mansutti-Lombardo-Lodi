package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

public class CCChooseOneToIsland extends Message{
    private MessageType messageType;
    private String nickname;
    private int islandNumber;
    private int cardPosition;
    private Color c;

    public CCChooseOneToIsland(String nickname, MessageType messageType, Color c, int islandNumber, int cardPosition) {
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

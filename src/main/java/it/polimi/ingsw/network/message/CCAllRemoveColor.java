package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

public class CCAllRemoveColor extends Message{

    private MessageType messageType;
    private String nickname;
    private Color color;

    public CCAllRemoveColor(String nickname, MessageType messageType, Color color) {
        super(nickname, MessageType.CC_ALL_REMOVE_COLOR);
        this.color=color;
    }

    public Color getColor() {
        return color;
    }
}

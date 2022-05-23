package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

public class MoveStudentToIsland extends Message{

    private MessageType messageType;
    private String nickname;
    private int islandNumber;
    private Color color;

    public MoveStudentToIsland(String nickname, MessageType messageType, int islandNumber, Color color) {
        super(nickname, MessageType.MOVE_STUDENT_TO_ISLAND);
        this.islandNumber=islandNumber;
        this.color=color;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Color getColor() {
        return color;
    }

    public int getIslandNumber() {
        return islandNumber;
    }
}

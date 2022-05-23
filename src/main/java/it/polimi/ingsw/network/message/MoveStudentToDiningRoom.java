package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

public class MoveStudentToDiningRoom extends Message{
    private MessageType messageType;
    private String nickname;
    private Color color;

    public MoveStudentToDiningRoom(String nickname, MessageType messageType, Color color) {
        super(nickname, MessageType.MOVE_STUDENT_TO_DINING_ROOM);
        this.color=color;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Color getColor() {
        return color;
    }

}

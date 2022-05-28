package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;

public class MoveStudentToDiningRoom extends Message{
    @Serial
    private static final long serialVersionUID = 7949035450798137820L;
    private final Color color;

    public MoveStudentToDiningRoom(String nickname, Color color) {
        super(nickname, MessageType.MOVE_STUDENT_TO_DINING_ROOM);
        this.color=color;
    }

    public Color getColor() {
        return color;
    }

}

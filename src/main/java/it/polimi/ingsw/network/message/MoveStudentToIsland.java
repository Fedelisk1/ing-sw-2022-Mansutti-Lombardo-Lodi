package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;

public class MoveStudentToIsland extends Message{

    @Serial
    private static final long serialVersionUID = -5948145573011567696L;
    private final int islandNumber;
    private final Color color;

    public MoveStudentToIsland(String nickname, int islandNumber, Color color) {
        super(nickname, MessageType.MOVE_STUDENT_TO_ISLAND);
        this.islandNumber=islandNumber;
        this.color=color;
    }

    public Color getColor() {
        return color;
    }

    public int getIslandNumber() {
        return islandNumber;
    }
}

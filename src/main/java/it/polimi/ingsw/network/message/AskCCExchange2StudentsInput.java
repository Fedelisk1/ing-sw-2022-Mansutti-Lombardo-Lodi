package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;
import java.util.List;

public class AskCCExchange2StudentsInput extends Message {

    @Serial
    private static final long serialVersionUID = 213216401144136079L;
    private final List<Color> entrance;
    private final List<Color> diningRoom;

    public AskCCExchange2StudentsInput(List<Color> entrance, List<Color> diningRoom) {
        super(SERVER_NICKNAME, MessageType.ASK_CC_EXCHANGE_2_STUDENTS_INPUT);

        this.entrance = entrance;
        this.diningRoom = diningRoom;
    }

    public List<Color> getEntrance() {
        return entrance;
    }

    public List<Color> getDiningRoom() {
        return diningRoom;
    }
}

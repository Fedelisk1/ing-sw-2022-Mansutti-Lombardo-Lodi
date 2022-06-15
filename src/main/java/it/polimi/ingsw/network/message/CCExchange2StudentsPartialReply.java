package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import javax.swing.*;
import java.io.Serial;

public class CCExchange2StudentsPartialReply extends Message {
    @Serial
    private static final long serialVersionUID = -2552946331236363749L;
    private final Color fromEntrance;
    private final Color fromDiningRoom;
    private final int inputCount;

    public CCExchange2StudentsPartialReply(String nickname, Color fromEntrance, Color fromDiningRoom, int inputCount) {
        super(nickname, MessageType.CC_EXCHANGE_2_STUDENTS_PARTIAL_REPLY);

        this.fromDiningRoom = fromDiningRoom;
        this.fromEntrance = fromEntrance;
        this.inputCount = inputCount;
    }

    public Color getFromDiningRoom() {
        return fromDiningRoom;
    }

    public Color getFromEntrance() {
        return fromEntrance;
    }

    public int getInputCount() {
        return inputCount;
    }
}

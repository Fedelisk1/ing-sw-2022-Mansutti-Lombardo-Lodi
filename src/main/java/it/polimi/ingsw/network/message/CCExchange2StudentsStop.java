package it.polimi.ingsw.network.message;

import java.io.Serial;

public class CCExchange2StudentsStop extends Message {
    @Serial
    private static final long serialVersionUID = -1825273000386720508L;

    public CCExchange2StudentsStop(String nickname) {
        super(nickname, MessageType.CC_EXCHANGE_2_STUDENTS_STOP);
    }
}

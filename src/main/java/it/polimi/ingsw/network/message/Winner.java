package it.polimi.ingsw.network.message;

import java.io.Serial;

public class Winner extends Message {

    @Serial
    private static final long serialVersionUID = 3453542527368391217L;

    public Winner() {
        super(SERVER_NICKNAME, MessageType.WINNER);
    }
}

package it.polimi.ingsw.network.message;

import java.io.Serial;

public class ErrorMessage extends Message {
    @Serial
    private static final long serialVersionUID = -3313882572669467864L;
    private final String error;

    public ErrorMessage(String nickname, String error) {
        super(nickname, MessageType.ERROR);
        this.error = error;
    }

    public String getError() {
        return error;
    }
}

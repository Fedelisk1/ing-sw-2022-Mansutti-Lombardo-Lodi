package it.polimi.ingsw.network.message;

import java.io.Serial;

/**
 * Sent from server to client as a response to LoginRequest
 */
public class LoginOutcome extends Message {
    @Serial
    private static final long serialVersionUID = 8104081940281074653L;
    private final boolean success;
    private final int gameId; // if gameId == -1 -> the client is going to be part of a new game


    public LoginOutcome(boolean success, int gameId) {
        super(Message.SERVER_NICKNAME, MessageType.LOGIN_OUTCOME);
        this.success = success;
        this.gameId = gameId;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getGameId() {
        return gameId;
    }
}

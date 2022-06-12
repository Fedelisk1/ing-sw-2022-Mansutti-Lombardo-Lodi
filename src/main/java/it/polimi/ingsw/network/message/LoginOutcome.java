package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Wizard;

import java.io.Serial;
import java.util.List;

/**
 * Sent from server to client as a response to LoginRequest
 */
public class LoginOutcome extends Message {
    @Serial
    private static final long serialVersionUID = 8104081940281074653L;
    private final boolean success;
    private final int gameId; // if gameId == -1 -> the client is going to be part of a new game
    private final List<Wizard> availableWizards;


    public LoginOutcome(boolean success, int gameId, List<Wizard> availableWizards) {
        super(Message.SERVER_NICKNAME, MessageType.LOGIN_OUTCOME);
        this.success = success;
        this.gameId = gameId;
        this.availableWizards = availableWizards;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getGameId() {
        return gameId;
    }

    public List<Wizard> getAvailableWizards() {
        return availableWizards;
    }
}

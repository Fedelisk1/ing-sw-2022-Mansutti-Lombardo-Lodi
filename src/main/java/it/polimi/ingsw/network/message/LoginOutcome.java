package it.polimi.ingsw.network.message;

/**
 * Sent from server to client as a response to LoginRequest
 */
public class LoginOutcome extends Message {
    private boolean nickAvailable; // nickname is available
    private boolean newGame; // tells the client whether he is going to be part of a new game or an existing one.
    private int gameId;


    LoginOutcome(boolean success) {
        super(Message.SERVER_NICKNAME, MessageType.LOGIN_OUTCOME);
        nickAvailable = success;
    }

    public boolean isNickAvailable() {
        return nickAvailable;
    }

    public void setNickAvailable(boolean nickAvailable) {
        this.nickAvailable = nickAvailable;
    }

    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
    }

    public boolean isNewGame() {
        return newGame;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }
}

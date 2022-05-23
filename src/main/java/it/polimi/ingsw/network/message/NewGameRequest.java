package it.polimi.ingsw.network.message;

/**
 * Sent from client to server when a new game has to start.
 */
public class NewGameRequest extends Message {
    private int players;
    private boolean expertMode;

    public NewGameRequest(String nickname, int players, boolean expertMode)
    {
        super(nickname, MessageType.NEW_GAME_REQUEST);
        this.players = players;
        this.expertMode = expertMode;
    }

    public int getPlayers()
    {
        return players;
    }
    public boolean isExpertMode()
    {
        return expertMode;
    }
}

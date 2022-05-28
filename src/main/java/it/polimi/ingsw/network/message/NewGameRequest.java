package it.polimi.ingsw.network.message;

import java.io.Serial;

/**
 * Sent from client to server when a new game has to start.
 */
public class NewGameRequest extends Message {
    @Serial
    private static final long serialVersionUID = 8991436861766198854L;
    private final int players;
    private final boolean expertMode;

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

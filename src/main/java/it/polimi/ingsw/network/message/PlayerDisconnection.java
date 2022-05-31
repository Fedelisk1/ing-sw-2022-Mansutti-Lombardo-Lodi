package it.polimi.ingsw.network.message;

import java.io.Serial;

public class PlayerDisconnection extends Message {
    @Serial
    private static final long serialVersionUID = 1714344561980652126L;
    private final String content;

    public PlayerDisconnection(String content) {
        super(null, MessageType.PLAYER_DISCONNECTION);

        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

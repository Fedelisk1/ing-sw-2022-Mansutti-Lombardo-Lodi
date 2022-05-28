package it.polimi.ingsw.network.message;

import javax.swing.*;
import java.io.Serial;
import java.util.List;

public class Lobby extends Message {
    @Serial
    private static final long serialVersionUID = -111339111956509156L;
    private final List<String> nicknames;
    private final int players;

    public Lobby(List<String> nicknames, int players) {
        super(Message.SERVER_NICKNAME, MessageType.LOBBY);
        this.nicknames = nicknames;
        this.players = players;
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    public int getPlayers() {
        return players;
    }
}

package it.polimi.ingsw.network.message;

import javax.swing.*;
import java.util.List;

public class Lobby extends Message {
    private List<String> nicknames;
    private int players;

    public Lobby(String nickname, List<String> nicknames, int players) {
        super(nickname, MessageType.LOBBY);
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

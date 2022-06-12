package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.reduced.ReducedPlayer;

import java.io.Serial;
import java.util.List;

public class Lobby extends Message {
    @Serial
    private static final long serialVersionUID = -111339111956509156L;
    private final List<ReducedPlayer> players;
    private final int playersNumber;

    public Lobby(List<ReducedPlayer> players, int playersNumber) {
        super(Message.SERVER_NICKNAME, MessageType.LOBBY);
        this.players = players;
        this.playersNumber = playersNumber;
    }

    public List<ReducedPlayer> getPlayers() {
        return players;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }
}

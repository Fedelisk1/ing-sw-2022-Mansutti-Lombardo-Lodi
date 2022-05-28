package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Game;

import java.io.Serial;

public class GameStart extends Message {

    @Serial
    private static final long serialVersionUID = -8830558098130521942L;

    public GameStart(String nickname) {
        super(nickname, MessageType.GAME_START);
    }

}

package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Game;

public class GameStart extends Message {

    public GameStart(String nickname) {
        super(nickname, MessageType.GAME_START);
    }

}

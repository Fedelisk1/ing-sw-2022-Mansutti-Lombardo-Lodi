package it.polimi.ingsw.network.message;

import java.io.Serial;

public class WinnerToOthers extends Message {
    @Serial
    private static final long serialVersionUID = 3453542527368391217L;
    private final String nick;

    public WinnerToOthers(String nick) {
        super(SERVER_NICKNAME, MessageType.WINNER_TO_OTHERS);
        this.nick = nick;
    }

    @Override
    public String getNickname() {
        return super.getNickname();
    }
}

package it.polimi.ingsw.network.message;

import java.io.Serial;

public class Shutdown extends Message {
    @Serial
    private static final long serialVersionUID = -3621775151685554784L;
    private final String content;

    public Shutdown(String content) {
        super(Message.SERVER_NICKNAME, MessageType.SHUTDOWN_CLIENT);
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}


package it.polimi.ingsw.network.message;

import java.io.Serial;

public class StringMessage extends Message {
    @Serial
    private static final long serialVersionUID = -7338863630798085531L;
    private final String content;

    public StringMessage(String content) {
        super(Message.SERVER_NICKNAME, MessageType.STRING_MESSAGE);
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

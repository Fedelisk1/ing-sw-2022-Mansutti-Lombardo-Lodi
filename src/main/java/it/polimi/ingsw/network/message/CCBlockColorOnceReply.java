package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;

public class CCBlockColorOnceReply extends Message {

    @Serial
    private static final long serialVersionUID = -7417154069659059419L;
    private final Color color;

    public CCBlockColorOnceReply(String nickname, Color color) {
        super(nickname, MessageType.CC_BLOCK_COLOR_ONCE_REPLY);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}

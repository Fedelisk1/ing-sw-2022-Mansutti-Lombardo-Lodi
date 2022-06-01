package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;

public class CCAllRemoveColorReply extends Message {
    @Serial
    private static final long serialVersionUID = 8189034441576245280L;
    private final Color color;

    public CCAllRemoveColorReply(String nickname, Color color) {
        super(nickname, MessageType.CC_ALL_REMOVE_COLOR_REPLY);

        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}

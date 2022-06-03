package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;

public class CCChoose1DiningRoomReply extends Message {

    @Serial
    private static final long serialVersionUID = -165018580165269740L;
    private final Color color;

    public CCChoose1DiningRoomReply(String nickname, Color color) {
        super(nickname, MessageType.CC_CHOOSE_1_DINING_ROOM_REPLY);
        this.color=color;
    }

    public Color getColor() {
        return color;
    }
}

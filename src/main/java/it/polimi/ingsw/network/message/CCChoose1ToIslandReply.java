package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;
import java.util.List;

public class CCChoose1ToIslandReply extends Message {

    @Serial
    private static final long serialVersionUID = -8551094678145011676L;
    private final Color color;
    private final int island;

    public CCChoose1ToIslandReply(String nickname, Color color, int island) {
        super(nickname, MessageType.CC_CHOOSE_1_TO_ISLAND_REPLY);
        this.color = color;
        this.island = island;
    }

    public Color getColor() {
        return color;
    }

    public int getIsland() {
        return island;
    }
}

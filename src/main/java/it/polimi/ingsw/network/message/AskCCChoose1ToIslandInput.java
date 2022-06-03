package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;
import java.util.List;

public class AskCCChoose1ToIslandInput extends Message{
    @Serial
    private static final long serialVersionUID = 3732250567974070317L;
    private final List<Color> allowedColors;
    private final int maxIsland;

    public AskCCChoose1ToIslandInput(List<Color> allowedColors, int maxIsland) {
        super(SERVER_NICKNAME, MessageType.ASK_CC_CHOOSE_1_TO_ISLAND_INPUT);
        this.allowedColors = allowedColors;
        this.maxIsland = maxIsland;
    }

    public List<Color> getAllowedColors() {
        return allowedColors;
    }

    public int getMaxIsland() {
        return maxIsland;
    }
}

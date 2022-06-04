package it.polimi.ingsw.network.message;

import java.io.Serial;

public class CCChooseIslandReply extends Message {

    @Serial
    private static final long serialVersionUID = -5926550410324022519L;
    private final int chosenIsland;

    public CCChooseIslandReply(String nickname, int chosenIsland) {
        super(nickname, MessageType.CC_CHOOSE_ISLAND_REPLY);

        this.chosenIsland = chosenIsland;
    }

    public int getChosenIsland() {
        return chosenIsland;
    }
}

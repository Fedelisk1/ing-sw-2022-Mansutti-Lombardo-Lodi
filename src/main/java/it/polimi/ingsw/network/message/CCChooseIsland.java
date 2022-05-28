package it.polimi.ingsw.network.message;

import java.io.Serial;

public class CCChooseIsland extends Message{
    @Serial
    private static final long serialVersionUID = 3732250567974070317L;
    private final int islnumb;
    private final int cardPosition;

    public CCChooseIsland(String nickname, int islnumb, int cardPosition) {
        super(nickname, MessageType.CC_CHOOSE_ISLAND);
        this.islnumb=islnumb;
        this.cardPosition=cardPosition;
    }

    public int getIslnumb() {
        return islnumb;
    }

    public int getCardPosition() {
        return cardPosition;
    }
}

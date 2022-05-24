package it.polimi.ingsw.network.message;

public class CCNoEntryIsland extends Message{
    private MessageType messageType;
    private String nickname;
    private int islNumb;
    private int cardPosition;

    public CCNoEntryIsland(String nickname, MessageType messageType, int islNumb, int cardPosition) {
        super(nickname, MessageType.CC_NO_ENTRY_ISLAND);
        this.islNumb=islNumb;
        this.cardPosition=cardPosition;
    }

    public int getIslNumb() {
        return islNumb;
    }

    public int getCardPosition() {
        return cardPosition;
    }
}

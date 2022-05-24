package it.polimi.ingsw.network.message;

public class CCChooseIsland extends Message{
    private MessageType messageType;
    private String nickname;
    private int islnumb;
    private int cardPosition;

    public CCChooseIsland(String nickname, MessageType messageType, int islnumb, int cardPosition) {
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

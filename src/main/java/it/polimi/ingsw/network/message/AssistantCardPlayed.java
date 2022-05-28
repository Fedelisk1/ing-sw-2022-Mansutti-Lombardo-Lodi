package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.AssistantCard;

import java.io.Serial;

/**
 * Sent from server to clients to notify that another player has chosen an assistant card.
 */
public class AssistantCardPlayed extends Message {
    @Serial
    private static final long serialVersionUID = 8074794019219959040L;
    private final String whoPlayed;
    private final int playedCard;

    public AssistantCardPlayed(String whoPlayed, int playedCard) {
        super(Message.SERVER_NICKNAME, MessageType.ASSISTANT_CARD_PLAYED);
        this.whoPlayed = whoPlayed;
        this.playedCard = playedCard;
    }

    public String getWhoPlayed() {
        return whoPlayed;
    }

    public int getPlayedCard() {
        return playedCard;
    }
}

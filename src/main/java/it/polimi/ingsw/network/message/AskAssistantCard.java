package it.polimi.ingsw.network.message;


import java.io.Serial;
import java.util.List;
import java.util.Map;

public class AskAssistantCard extends Message {
    @Serial
    private static final long serialVersionUID = -4694720879666871428L;
    private final Map<Integer, Integer> hand;
    private final List<Integer> notPlayable;

    public AskAssistantCard(Map<Integer, Integer> hand, List<Integer> notPlayable) {
        super(Message.SERVER_NICKNAME, MessageType.ASK_ASSISTANT_CARD);
        this.hand = hand;
        this.notPlayable = notPlayable;
    }

    public Map<Integer, Integer> getHand() {
        return hand;
    }

    public List<Integer> getNotPlayable() {
        return notPlayable;
    }
}

package it.polimi.ingsw.network.message;

import java.io.Serial;

public class PlayAssistantCard extends Message{
    @Serial
    private static final long serialVersionUID = 5592465921881067476L;
    int chosenCard;

    public PlayAssistantCard(String nickname, int chosenCard)
    {
        super(nickname, MessageType.PLAY_ASSISTANT_CARD);
        this.chosenCard=chosenCard;
    }

    public int getChosenCard() {
        return chosenCard;
    }
}

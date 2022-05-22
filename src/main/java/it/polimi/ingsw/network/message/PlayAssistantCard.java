package it.polimi.ingsw.network.message;

public class PlayAssistantCard extends Message{
    int chosenCard;

    public PlayAssistantCard(String nickname,int chosenCard)
    {
        super(nickname,MessageType.PLAY_ASSISTANT_CARD);
        this.chosenCard=chosenCard;
    }

    public int getChosenCard() {
        return chosenCard;
    }
}

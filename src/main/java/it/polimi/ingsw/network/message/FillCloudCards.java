package it.polimi.ingsw.network.message;

public class FillCloudCards extends Message{
    private MessageType messageType;
    private String nickname;

    public FillCloudCards(String nickname, MessageType messageType) {
        super(nickname, MessageType.FILL_CLOUD_CARDS);
    }

    public MessageType getMessageType() {
        return messageType;
    }
}

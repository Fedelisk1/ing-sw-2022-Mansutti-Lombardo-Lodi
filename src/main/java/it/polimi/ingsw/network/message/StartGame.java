package it.polimi.ingsw.network.message;

public class StartGame extends Message {
    private MessageType messageType;
    private String nickname;

    public StartGame(String nickname, MessageType messageType) {
        super(nickname, MessageType.START_GAME);
    }

    public MessageType getMessageType() {
        return messageType;
    }
}

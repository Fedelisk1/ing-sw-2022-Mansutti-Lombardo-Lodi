package it.polimi.ingsw.network.message;

public class EndPlayerTurn extends Message{

    private MessageType messageType;
    private String nickname;

    public EndPlayerTurn(String nickname, MessageType messageType) {
        super(nickname, MessageType.END_PLAYER_TURN);
    }
}

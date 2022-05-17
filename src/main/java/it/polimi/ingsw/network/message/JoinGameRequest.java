package it.polimi.ingsw.network.message;

public class JoinGameRequest extends Message{
    private String nickname;

    public JoinGameRequest(String nickname) {
        super(MessageType.JOIN_GAME_REQUEST);
        this.nickname = nickname;
    }

    @Override
    public String toString(){
        return nickname;
    }
}

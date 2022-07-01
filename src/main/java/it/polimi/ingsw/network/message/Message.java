package it.polimi.ingsw.network.message;
import java.io.Serial;
import java.io.Serializable;

public class Message implements Serializable{
    @Serial
    private static final long serialVersionUID = 3787705488330519611L;
    public static String SERVER_NICKNAME = "SERVER";
    private final MessageType messageType;
    private final String nickname;

    public Message(String nickname, MessageType messageType) {
        this.messageType = messageType;
        this.nickname = nickname;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString() {
        return "nickname -> " + nickname;
    }
}

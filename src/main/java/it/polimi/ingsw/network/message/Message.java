package it.polimi.ingsw.network.message;
import java.io.Serializable;

public class Message implements Serializable{
    public static String SERVER_NICKNAME = "SERVER";
    private MessageType messageType;
    private String nickname;

    Message(String nickname, MessageType messageType) {
        this.messageType = messageType;
        this.nickname=nickname;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString() {
        return "nickname"+nickname;
    }

}

package it.polimi.ingsw.network.message;
import java.io.Serializable;

public class Message implements Serializable{
    public static String SERVER_NICKNAME = "SERVER";
    private MessageType messageType;

    Message(String nickname, MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }
    @Override
    public String toString() {
        return "";
    }

}

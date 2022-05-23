package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

public class ChooseCloudCard extends Message{
    private MessageType messageType;
    private String nickname;
    private int cloudCard;

    public ChooseCloudCard(String nickname, MessageType messageType, int cloudCard) {
        super(nickname, MessageType.CHOOSE_CLOUD_CARD);
        this.cloudCard=cloudCard;
    }

    public int getCloudCard() {
        return cloudCard;
    }
}

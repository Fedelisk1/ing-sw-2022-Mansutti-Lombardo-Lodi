package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;

public class ChooseCloudCard extends Message{
    @Serial
    private static final long serialVersionUID = -3254141254523935132L;
    private final int cloudCard;

    public ChooseCloudCard(String nickname, int cloudCard) {
        super(nickname, MessageType.CHOOSE_CLOUD_CARD);
        this.cloudCard=cloudCard;
    }

    public int getCloudCard() {
        return cloudCard;
    }
}

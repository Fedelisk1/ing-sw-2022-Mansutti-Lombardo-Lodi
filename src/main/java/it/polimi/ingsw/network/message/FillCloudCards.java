package it.polimi.ingsw.network.message;

import java.io.Serial;

public class FillCloudCards extends Message{
    @Serial
    private static final long serialVersionUID = 7766247160934041802L;

    public FillCloudCards(String nickname) {
        super(nickname, MessageType.FILL_CLOUD_CARDS);
    }
}

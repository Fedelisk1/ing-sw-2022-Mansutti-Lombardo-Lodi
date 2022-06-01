package it.polimi.ingsw.network.message;

import java.io.Serial;

public class AskCCAllRemoveColorInput extends Message {

    @Serial
    private static final long serialVersionUID = -8370580852444641736L;

    public AskCCAllRemoveColorInput() {
        super(Message.SERVER_NICKNAME, MessageType.ASK_CC_ALL_REMOVE_COLOR_INPUT);
    }
}

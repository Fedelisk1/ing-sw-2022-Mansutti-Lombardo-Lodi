package it.polimi.ingsw.network.message;

import java.io.Serial;

public class AskCCBlockColorOnceInput extends Message{
    @Serial
    private static final long serialVersionUID = -324281563800112314L;

    public AskCCBlockColorOnceInput() {
        super(Message.SERVER_NICKNAME, MessageType.ASK_CC_BLOCK_COLOR_ONCE_INPUT);
    }
}

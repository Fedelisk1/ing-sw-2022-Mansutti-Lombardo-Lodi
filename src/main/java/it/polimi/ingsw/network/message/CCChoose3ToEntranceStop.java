package it.polimi.ingsw.network.message;

import java.io.Serial;

public class CCChoose3ToEntranceStop extends Message {
    @Serial
    private static final long serialVersionUID = -579951159575130456L;

    public CCChoose3ToEntranceStop(String nickname) {
        super(nickname, MessageType.CC_CHOOSE_3_TO_ENTRANCE_STOP);
    }
}

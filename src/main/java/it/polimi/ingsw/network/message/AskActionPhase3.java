package it.polimi.ingsw.network.message;

import java.io.Serial;
import java.util.List;

public class AskActionPhase3 extends Message {
    @Serial
    private static final long serialVersionUID = -128722959852750841L;
    private final List<Integer> alloweValues;

    public AskActionPhase3(List<Integer> alloweValues) {
        super(SERVER_NICKNAME, MessageType.ASK_ACTION_PHASE_3);
        this.alloweValues = alloweValues;
    }

    public List<Integer> getAlloweValues() {
        return alloweValues;
    }
}

package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;
import java.util.List;
import java.util.Set;

public class AskCCChoose1DiningRoomInput extends Message {

    @Serial
    private static final long serialVersionUID = -6502186183215072829L;
    private final List<Color> allowedValues;

    public AskCCChoose1DiningRoomInput(List<Color> allowedValues) {
        super(SERVER_NICKNAME, MessageType.ASK_CC_CHOOSE_1_DINING_ROOM_INPUT);

        this.allowedValues = allowedValues;
    }

    public List<Color> getAllowedValues() {
        return allowedValues;
    }
}

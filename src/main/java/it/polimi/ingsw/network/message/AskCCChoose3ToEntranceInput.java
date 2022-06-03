package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;
import java.util.List;

public class AskCCChoose3ToEntranceInput extends Message {

    @Serial
    private static final long serialVersionUID = 6056681633104176196L;
    private final List<Color> allowedFromCard;
    private final List<Color> allowedFromEntrance;

    public AskCCChoose3ToEntranceInput(List<Color> allowedFromCard, List<Color> allowedFromEntrance) {
        super(SERVER_NICKNAME, MessageType.ASK_CC_CHOOSE_3_TO_ENTRANCE_INPUT);

        this.allowedFromCard = allowedFromCard;
        this.allowedFromEntrance = allowedFromEntrance;
    }

    public List<Color> getAllowedFromCard() {
        return allowedFromCard;
    }

    public List<Color> getAllowedFromEntrance() {
        return allowedFromEntrance;
    }
}


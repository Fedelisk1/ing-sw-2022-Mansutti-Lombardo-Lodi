package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;

public class CCChoose3ToEntrancePartialReply extends Message {

    @Serial
    private static final long serialVersionUID = 4810898318497487851L;
    private final Color chosenFromEntrance;
    private final Color chosenFromCard;
    private final int inputCount;

    public CCChoose3ToEntrancePartialReply(String nickname, Color chosenFromCard, Color chosenFromEntrance, int inputCount) {
        super(nickname, MessageType.CC_CHOOSE_3_TO_ENTRANCE_PARTIAL_REPLY);
        this.chosenFromEntrance = chosenFromEntrance;
        this.chosenFromCard = chosenFromCard;

        this.inputCount = inputCount;
    }

    public Color getChosenFromCard() {
        return chosenFromCard;
    }

    public Color getChosenFromEntrance() {
        return chosenFromEntrance;
    }

    public int getInputCount() {
        return inputCount;
    }
}

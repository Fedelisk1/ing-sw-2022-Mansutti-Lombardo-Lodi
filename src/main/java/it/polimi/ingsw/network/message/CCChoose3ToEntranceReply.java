package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;
import java.util.EnumMap;

public class CCChoose3ToEntranceReply extends Message {
    @Serial
    private static final long serialVersionUID = 1840754576829237367L;
    private final EnumMap<Color, Integer> chosenFromCard;
    private final EnumMap<Color, Integer> chosenFromEntrance;

    public CCChoose3ToEntranceReply(String nickname, EnumMap<Color, Integer> chosenFromCard, EnumMap<Color, Integer> chosenFromEntrance){
        super(nickname, MessageType.CC_CHOOSE_3_TO_ENTRANCE_REPLY);
        this.chosenFromCard=chosenFromCard;
        this.chosenFromEntrance=chosenFromEntrance;
    }

    public EnumMap<Color, Integer> getChosenFromCard() {
        return chosenFromCard;
    }

    public EnumMap<Color, Integer> getChosenFromEntrance() {
        return chosenFromEntrance;
    }
}

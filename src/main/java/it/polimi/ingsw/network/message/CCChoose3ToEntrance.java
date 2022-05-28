package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;
import java.util.EnumMap;

public class CCChoose3ToEntrance extends Message {
    @Serial
    private static final long serialVersionUID = 1840754576829237367L;
    private final EnumMap<Color, Integer> chosenFromCard;
    private final EnumMap<Color, Integer> chosenFromEntrance;
    private final int cardPosition;

    public CCChoose3ToEntrance(String nickname, EnumMap<Color, Integer> chosenFromCard, EnumMap<Color, Integer> chosenFromEntrance, int cardPosition){
        super(nickname, MessageType.CC_CHOOSE_3_TO_ENTRANCE);
        this.chosenFromCard=chosenFromCard;
        this.chosenFromEntrance=chosenFromEntrance;
        this.cardPosition=cardPosition;
    }

    public EnumMap<Color, Integer> getChosenFromCard() {
        return chosenFromCard;
    }

    public EnumMap<Color, Integer> getChosenFromEntrance() {
        return chosenFromEntrance;
    }

    public int getCardPosition() {
        return cardPosition;
    }
}

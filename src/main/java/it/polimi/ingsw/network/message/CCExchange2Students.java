package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.io.Serial;
import java.util.EnumMap;

public class CCExchange2Students extends Message {
    @Serial
    private static final long serialVersionUID = -6468602063016590475L;
    private final EnumMap<Color, Integer> chosenFromEntrance;
    private final EnumMap<Color, Integer> chosenFromDiningRoom;
    private final int cardPosition;

    public CCExchange2Students(String nickname, EnumMap<Color, Integer> chosenFromEntrance, EnumMap<Color, Integer> chosenFromDiningRoom, int cardPosition) {
        super(nickname, MessageType.CC_EXCHANGE_2_STUDENTS);
        this.chosenFromDiningRoom=chosenFromDiningRoom;
        this.chosenFromEntrance=chosenFromEntrance;
        this.cardPosition=cardPosition;
    }

    public EnumMap<Color, Integer> getChosenFromEntrance() {
        return chosenFromEntrance;
    }

    public EnumMap<Color, Integer> getChosenFromDiningRoom() {
        return chosenFromDiningRoom;
    }

    public int getCardPosition() {
        return cardPosition;
    }
}

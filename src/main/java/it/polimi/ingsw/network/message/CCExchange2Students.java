package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Color;

import java.util.EnumMap;

public class CCExchange2Students extends Message {
    private MessageType messageType;
    private String nickname;
    private EnumMap<Color, Integer> chosenFromEntrance;
    private EnumMap<Color, Integer> chosenFromDiningRoom;
    private int cardPosition;

    public CCExchange2Students(String nickname, MessageType messageType, EnumMap<Color, Integer> chosenFromEntrance, EnumMap<Color, Integer> chosenFromDiningRoom, int cardPosition) {
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
